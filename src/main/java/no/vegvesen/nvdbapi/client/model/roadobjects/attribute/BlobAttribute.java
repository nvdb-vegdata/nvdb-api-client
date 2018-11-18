package no.vegvesen.nvdbapi.client.model.roadobjects.attribute;

public class BlobAttribute extends Attribute {

    private final Integer blobId;
    private final String blobFormat;
    private final String href;

    public BlobAttribute(int id,
                         Integer blobId,
                         String blobFormat,
                         String href) {
        super(id);
        this.blobId = blobId;
        this.blobFormat = blobFormat;
        this.href = href;
    }

    @Override
    public AttributeType getAttributeType() {
        return AttributeType.BLOB;
    }

    public String getBlobFormat() {
        return blobFormat;
    }

    public Integer getBlobId() {
        return blobId;
    }

    public String getHref() {
        return href;
    }
}
