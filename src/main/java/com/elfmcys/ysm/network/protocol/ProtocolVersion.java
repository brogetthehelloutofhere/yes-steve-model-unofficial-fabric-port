package com.elfmcys.ysm.network.protocol;

/** Exact version and feature requirements for the current unstable protocol. */
public final class ProtocolVersion {
    public static final String CHANNEL_PATH = "protocol";
    public static final String CURRENT = "0.1.0-unstable";
    public static final String TRANSPORT_VERSION = CURRENT;
    public static final int ASSET_TRANSFER_RELEASE_FEATURE_ID = 1;
    public static final int CLIENT_HOSTED_MODELS_FEATURE_ID = 2;

    public static boolean supportsClientHostedModels(PeerProtocolProfile profile) {
        return profile.protocolVersion().equals(CURRENT)
                && profile.featureIds().contains(CLIENT_HOSTED_MODELS_FEATURE_ID);
    }

    public static boolean supportsRequiredFeatures(PeerProtocolProfile profile) {
        return profile.protocolVersion().equals(CURRENT)
                && profile.featureIds().contains(ASSET_TRANSFER_RELEASE_FEATURE_ID);
    }

    private ProtocolVersion() {
    }
}
