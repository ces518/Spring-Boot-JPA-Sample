package me.june.document.domain.document;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.june.document.domain.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "documents")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Document extends BaseEntity {
	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Category category;

	@Column(nullable = false)
	private String contents;

	public Document(final String title, final Category category, final String contents) {
		this.title = title;
		this.category = category;
		this.contents = contents;
	}

	public void update(final Document entity) {
		this.title = entity.getTitle();
		this.category = entity.getCategory();
		this.contents = entity.getContents();
	}

	// 문서 분류
	public enum Category {
		// 지출
		EXPENSE,
		// 휴가
		VACATION,
		;
	}
}
