package me.june.document.domain.document.dto;

import me.june.document.domain.document.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class DocumentRequest {
	@NotEmpty
	private String title;

	@NotNull
	private Document.Category category;

	@NotEmpty
	private String contents;

	public Document toEntity() {
		return new Document(this.title, this.category, this.contents);
	}
}
