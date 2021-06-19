package me.june.document.domain.document;

import lombok.RequiredArgsConstructor;
import me.june.document.domain.document.dto.DocumentRequest;
import me.june.document.domain.document.dto.DocumentView;
import me.june.document.domain.document.errors.DocumentNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {
	private final DocumentService service;

	@GetMapping
	public ResponseEntity<Page<DocumentView>> getDocuments(@PageableDefault final Pageable pageable) {
		final Page<DocumentView> documents = service.getDocuments(pageable);
		return ResponseEntity.ok(documents);
	}

	@GetMapping("{id}")
	public ResponseEntity<DocumentView> getDocument(@PathVariable final Long id) {
		final DocumentView document = service.getDocument(id);
		return ResponseEntity.ok(document);
	}

	@PostMapping
	public ResponseEntity<DocumentView> createDocument(
		@RequestBody final DocumentRequest request,
		final BindingResult result
	) {
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}
		final long documentId = service.createDocument(request);
		final DocumentView document = service.getDocument(documentId);
		return ResponseEntity.status(HttpStatus.CREATED).body(document);
	}

	@PutMapping("{id}")
	public ResponseEntity<DocumentView> updateDocument(
		@PathVariable final Long id,
		@RequestBody final DocumentRequest request,
		final BindingResult result
	) {
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}
		service.updateDocument(id, request);
		final DocumentView document = service.getDocument(id);
		return ResponseEntity.ok(document);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteDocument(@PathVariable final Long id) {
		service.deleteDocument(id);
		return ResponseEntity.ok().build();
	}

	@ExceptionHandler(DocumentNotFoundException.class)
	public ResponseEntity<Void> handleDocumentNotFound(DocumentNotFoundException e) {
		return ResponseEntity.notFound().build();
	}
}
