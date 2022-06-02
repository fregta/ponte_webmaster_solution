package hu.ponte.hr.dto;

import hu.ponte.hr.domain.Image;
import lombok.Builder;
import lombok.Getter;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLConnection;


/**
 * @author zoltan
 */
@Getter
public class ImageMeta {
	private String id;
	private String name;
	private String mimeType;
	private long size;
	private String digitalSign;

	public ImageMeta() {
	}

	public ImageMeta(Image image) {
		this.id = String.valueOf(image.getId());
		this.name = image.getFileName();
		this.mimeType = image.getMimeType();
		this.size = image.getSize();
		this.digitalSign = image.getEncodedDigitalSignature();
	}
}
