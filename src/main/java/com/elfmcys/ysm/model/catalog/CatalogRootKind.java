
package com.elfmcys.ysm.model.catalog;

import com.elfmcys.ysm.model.source.AccessPolicy;

/**
 * Scanner-local root classification.
 * Never appears in source-neutral catalogs or the unstable protocol.
 */
public enum CatalogRootKind {
    BUILTIN("builtin", AccessPolicy.PUBLIC),
    CUSTOM("custom", AccessPolicy.PUBLIC),

    /**
     * Legacy custom directory:
     * config/yes_steve_model/custom
     *
     * Kept separate from CUSTOM so inventory and commit validation
     * can distinguish the two physical roots.
     */
    LEGACY_CUSTOM("custom", AccessPolicy.PUBLIC),

    AUTH("auth", AccessPolicy.SESSION_AUTHORIZED);

    private final String namespace;
    private final AccessPolicy accessPolicy;

    CatalogRootKind(String namespace, AccessPolicy accessPolicy) {
        this.namespace = namespace;
        this.accessPolicy = accessPolicy;
    }

    public String namespace() {
        return namespace;
    }

    public AccessPolicy accessPolicy() {
        return accessPolicy;
    }
}
