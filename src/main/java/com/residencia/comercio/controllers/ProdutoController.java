package com.residencia.comercio.controllers;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.residencia.comercio.dtos.ProdutoDTO;
import com.residencia.comercio.entities.Produto;
import com.residencia.comercio.exceptions.NoSuchElementFoundException;
import com.residencia.comercio.services.ProdutoService;

@RestController
@RequestMapping("/produto")
@Validated
public class ProdutoController {
	@Autowired
	ProdutoService produtoService;

	@GetMapping
	public ResponseEntity<List<Produto>> findAllProduto() {
		List<Produto> produtoList = produtoService.findAllProduto();

		if (produtoList.isEmpty()) {
			throw new NoSuchElementFoundException("Nenhum produto encontrado.");
		} else {
			return new ResponseEntity<>(produtoList, HttpStatus.OK);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Produto> findProdutoById(@PathVariable Integer id) {
		Produto produto = produtoService.findProdutoById(id);
		if (produto == null) {
			throw new NoSuchElementFoundException("O Produto de id = " + id + " não foi encontrado.");
		} else {
			return new ResponseEntity<>(produto, HttpStatus.OK);
		}
	}

	@GetMapping("/dto/{id}")
	public ResponseEntity<ProdutoDTO> findProdutoDTOById(@PathVariable Integer id) {
		ProdutoDTO produtoDTO = produtoService.findProdutoDTOById(id);

		if (produtoDTO == null) {
			throw new NoSuchElementFoundException("Não foi encontrado Produto com o id = " + id + ".");
		} else {
			return new ResponseEntity<>(produtoDTO, HttpStatus.OK);
		}
	}

//	Request - localhost:8080/comercio/produto/query?sku=
	@GetMapping("/query")
	public ResponseEntity<Produto> findByIdQuery(
			@RequestParam @NotBlank(message = "O sku deve ser preenchido.") String sku) {
		return new ResponseEntity<>(null, HttpStatus.CONTINUE);
	}

//	Request - localhost:8080/comercio/produto/request?id=
	@GetMapping("/request")
	public ResponseEntity<Produto> findByIdRequest(
			@RequestParam @NotBlank(message = "O id deve ser preenchido.") Integer id) {
		return new ResponseEntity<>(null, HttpStatus.CONTINUE);
	}

	@PostMapping
	public ResponseEntity<Produto> saveProduto(@Valid @RequestBody Produto produto) {
		return new ResponseEntity<>(produtoService.saveProduto(produto), HttpStatus.CREATED);
	}

	@PostMapping("/dto")
	public ResponseEntity<ProdutoDTO> saveProdutoDTO(@RequestBody ProdutoDTO produtoDTO) {
		return new ResponseEntity<>(produtoService.saveProdutoDTO(produtoDTO), HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<Produto> updateProduto(@Valid @RequestBody Produto produto) {
		return new ResponseEntity<>(produtoService.updateProduto(produto), HttpStatus.OK);
	}

//	@PutMapping("/{id}")
//	public ResponseEntity<String> updateProdutoById(@PathVariable Integer id, @RequestBody Produto produto) {
//		Produto produtoAtualizado = produtoService.updateProdutoById(produto, id);
//		
//		if (null == produtoAtualizado) {
//			return new ResponseEntity<>(produtoAtualizado, HttpStatus.BAD_REQUEST);
//		} else {
//			return new ResponseEntity<>(produtoAtualizado, HttpStatus.OK);
//		}
//	}

	@DeleteMapping
	public ResponseEntity<String> deletePoduto(Produto produto) {
		if (produtoService.findProdutoById(produto.getIdProduto()) == null) {
			return new ResponseEntity<>(
					"Não foi possível excluir. O Produto de id = " + produto.getIdProduto() + " não foi encontrado.",
					HttpStatus.NOT_FOUND);
		} else {
			produtoService.deleteProduto(produto);
			return new ResponseEntity<>("O Produto de id = " + produto.getIdProduto() + " foi excluído com sucesso.",
					HttpStatus.OK);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProdutoById(@PathVariable Integer id) {
		if (produtoService.findProdutoById(id) == null) {
			return new ResponseEntity<>("Não foi possível excluir. O Produto de id = " + id + " não foi encontrado.",
					HttpStatus.NOT_FOUND);
		} else {
			produtoService.deleteProdutoById(id);
			return new ResponseEntity<>("O Produto de id = " + id + " foi excluído com sucesso.", HttpStatus.OK);
		}
	}

}
