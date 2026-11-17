
package com.elfmcys.ysm.model.catalog;

import com.elfmcys.ysm.model.domain.ModelPackDescriptor;
import com.elfmcys.ysm.model.domain.ModelScanReport;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerCatalogEncoderTest {

    @Test
    void fullCatalogPrefersModernCustomPackOverLegacyDuplicate() throws IOException {
        var epoch = UUID.randomUUID();

        var modern = pack(CatalogRootKind.CUSTOM, "Modern pack");
        var legacy = pack(CatalogRootKind.LEGACY_CUSTOM, "Legacy pack");

        var snapshot = snapshot(epoch, 1, List.of(legacy, modern));

        var decoded = RemoteCatalogDecoder.decodeList(
                ServerCatalogEncoder.encodeList(snapshot));

        assertEquals(1, decoded.packs().size());
        assertEquals("Modern pack", decoded.packs().get(0).name());
        assertEquals("custom", decoded.packs().get(0).subject().namespace());
    }

    @Test
    void deltaPrefersModernCustomPackOverLegacyDuplicate() throws IOException {
        var epoch = UUID.randomUUID();

        var previous = snapshot(epoch, 1, List.of());

        var modern = pack(CatalogRootKind.CUSTOM, "Modern pack");
        var legacy = pack(CatalogRootKind.LEGACY_CUSTOM, "Legacy pack");

        var next = snapshot(epoch, 2, List.of(legacy, modern));

        var base = RemoteCatalogDecoder.decodeList(
                ServerCatalogEncoder.encodeList(previous));

        var diff = CatalogDiff.between(previous, next);

        var updated = RemoteCatalogDecoder.applyDelta(
                base,
                ServerCatalogEncoder.encodeDelta(previous, next, diff));

        assertEquals(1, updated.packs().size());
        assertEquals("Modern pack", updated.packs().get(0).name());
        assertEquals("custom", updated.packs().get(0).subject().namespace());
    }

    private static ModelPackDescriptor pack(
            CatalogRootKind rootKind,
            String name) {

        return new ModelPackDescriptor(
                rootKind,
                "shared",
                name,
                "",
                Map.of(),
                new byte[0],
                "",
                0);
    }

    private static ServerCatalogSnapshot snapshot(
            UUID epoch,
            long revision,
            List<ModelPackDescriptor> packs) {

        return new ServerCatalogSnapshot(
                epoch,
                revision,
                List.of(),
                packs,
                ModelScanReport.empty());
    }
}
