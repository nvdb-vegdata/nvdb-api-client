package no.vegvesen.nvdbapi.client.model.roadobjects.attribute;

import java.util.Objects;

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

    @Override
    public String getValueAsString() {
        return href;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BlobAttribute that = (BlobAttribute) o;
        return Objects.equals(blobId, that.blobId) &&
            Objects.equals(blobFormat, that.blobFormat) &&
            Objects.equals(href, that.href);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), blobId, blobFormat, href);
    }
}
