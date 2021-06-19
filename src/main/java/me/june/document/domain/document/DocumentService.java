package me.june.document.domain.document;

import lombok.RequiredArgsConstructor;
import me.june.document.domain.document.dto.DocumentRequest;
import me.june.document.domain.document.dto.DocumentView;
import me.june.document.domain.document.errors.DocumentNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DocumentService {
	private final DocumentRepository repository;

	public Page<DocumentView> getDocuments(final Pageable pageable) {
		return repository.findAll(pageable)
				.map(DocumentView::from);
	}

	public DocumentView getDocument(final long id) {
		final Document document = findById(id);
		return DocumentView.from(document);
	}

	@Transactional
	public long createDocument(final DocumentRequest request) {
		final Document document = request.toEntity();
		return repository.save(document).getId();
	}

	@Transactional
	public void updateDocument(final long id, final DocumentRequest request) {
		final Document document = findById(id);
		document.update(request.toEntity());
	}

	@Transactional
	public void deleteDocument(final long id) {
		final Document document = findById(id);
		repository.delete(document);
	}

	private Document findById(final long id) {
		return repository.findById(id)
				.orElseThrow(DocumentNotFoundException::new);
	}
}
