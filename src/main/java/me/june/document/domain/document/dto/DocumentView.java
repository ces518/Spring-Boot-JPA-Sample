package me.june.document.domain.document.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.june.document.domain.document.Document;

@Getter
@RequiredArgsConstructor
public class DocumentView {
	private final long id;
	private final String title;
	private final Document.Category category;
	private final String contents;

	public static DocumentView from(final Document document) {
		return new DocumentView(
			document.getId(),
			document.getTitle(),
			document.getCategory(),
			document.getContents()
		);
	}
}
