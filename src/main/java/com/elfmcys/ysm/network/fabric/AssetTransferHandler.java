package com.elfmcys.ysm.network.fabric;

import com.elfmcys.ysm.client.model.ClientModelService;
import com.elfmcys.ysm.model.server.ServerModelService;
import com.elfmcys.ysm.network.NetworkHandler;
import com.elfmcys.ysm.network.NetworkPayload;
import com.elfmcys.ysm.network.protocol.ProtocolUuid;
import com.elfmcys.ysm.network.protocol.ProtocolVersion;
import com.elfmcys.ysm.proto.network.protocol.v0.AssetTransferV0;


public final class AssetTransferHandler {
    private AssetTransferHandler() {
    }

    public static void handleFragmentPayload(NetworkPayload<AssetTransferV0.AssetFragment> payload,
                                             YsmNetContext context) {
        if (context.sender() == null) {
            try {
                context.enqueueWork(() -> {
                    try (payload) {
                        ClientModelService.current().ifPresent(service -> service.receive(payload));
                    }
                });
            } catch (Throwable error) {
                payload.close();
                throw error;
            }
        } else {
            payload.close();
        }
    }

    public static void handleBatchRequest(AssetTransferV0.ModelAssetBatchRequest request,
                                          YsmNetContext context) {
        var sender = context.sender();
        if (sender != null) {
            context.enqueueWork(() -> ServerModelService.current()
                    .ifPresent(service -> service.handleRequest(sender, request)));
        }
    }

    public static void handleBatchFailure(AssetTransferV0.ModelAssetBatchFailure failure,
                                          YsmNetContext context) {
        if (context.sender() == null) {
            context.enqueueWork(() -> ClientModelService.current()
                    .ifPresent(service -> service.requestFailed(failure)));
        }
    }

    public static void handleCatalogResync(AssetTransferV0.CatalogResyncRequest request,
                                           YsmNetContext context) {
        var sender = context.sender();
        if (sender != null) {
            context.enqueueWork(() -> ServerModelService.current()
                    .ifPresent(service -> service.sendCatalog(sender)));
        }
    }

    public static void handleBatchCancel(AssetTransferV0.ModelAssetBatchCancel cancel,
                                         YsmNetContext context) {
        var sender = context.sender();
        if (sender != null) {
            try {
                var requestId = ProtocolUuid.get(cancel.getRequestId());
                context.enqueueWork(() -> ServerModelService.current()
                        .ifPresent(service -> service.cancelSession(sender.getUUID(), requestId)));
            } catch (RuntimeException ignored) {
            }
        }
    }

    public static void handleTransferRelease(AssetTransferV0.AssetTransferRelease release,
                                             YsmNetContext context) {
        var sender = context.sender();
        if (sender != null) {
            context.enqueueWork(() -> ServerModelService.current()
                    .ifPresent(service -> service.releaseTransfer(sender.getUUID(), release)));
        }
    }

    /** Receives client-hosted model fragments after feature negotiation. */
    public static void handleClientHostedModelUploadFragment(
            NetworkPayload<AssetTransferV0.ClientHostedModelUploadFragment> payload,
            YsmNetContext context) {
        var sender = context.sender();
        if (sender == null) {
            payload.close();
            return;
        }

        var profile = NetworkHandler.peerProfile(context.connection()).orElse(null);
        if (profile == null || !ProtocolVersion.supportsClientHostedModels(profile)) {
            payload.close();
            return;
        }

        final NetworkPayload<AssetTransferV0.AssetFragment> fragmentPayload;
        try {
            var fragment = payload.protobuf().getFragment();
            fragmentPayload = payload.raw()
                    .<NetworkPayload<AssetTransferV0.AssetFragment>>map(
                            raw -> NetworkPayload.withRaw(fragment, raw.acquire()))
                    .orElseGet(() -> NetworkPayload.protobuf(fragment));
        } finally {
            payload.close();
        }

        try {
            context.enqueueWork(() -> ServerModelService.current().ifPresentOrElse(
                    service -> service.receiveClientHostedModelUpload(sender, fragmentPayload),
                    fragmentPayload::close));
        } catch (Throwable error) {
            fragmentPayload.close();
            throw error;
        }
    }

    /** Upload-credit endpoint placeholder; the client sender is added separately. */
    public static void handleClientHostedModelUploadCredit(
            AssetTransferV0.ClientHostedModelUploadCredit credit, YsmNetContext context) {
        // No upload sender is active yet.
    }
}
