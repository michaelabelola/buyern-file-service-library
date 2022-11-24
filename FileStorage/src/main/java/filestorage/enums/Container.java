package filestorage.enums;

/**
 * Container Type.
 * Every entity has two {@link Container}s: PRIVATE and PUBLIC.
 */
public enum Container {
    /**
     * should contain items that can only be accessed by stakeholders of the entity. such as:
     * unreleased product images, cash flow, private assets etc
     */
    PRIVATE,
    /**
     * should contain items that can be accessed by anyone. such as:
     * product images, delivery vehicle images etc
     */
    PUBLIC
}
