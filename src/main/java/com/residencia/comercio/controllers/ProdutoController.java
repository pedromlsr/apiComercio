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
import com.residencia.comercio.exceptions.ErrorResponse;
import com.residencia.comercio.exceptions.NoSuchElementFoundException;
import com.residencia.comercio.services.ProdutoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/produto")
@Validated
public class ProdutoController {
	@Autowired
	ProdutoService produtoService;

	@GetMapping
	@Operation(summary = "Busca todos os produtos cadastrados.", responses = {
			@ApiResponse(responseCode = "200", description = "Sucesso. Retorna todos os produtos cadastrados.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Produto.class))),
			@ApiResponse(responseCode = "404", description = "Falha. Nenhum produto encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Falha. Erro inesperado.", content = @Content) })
	public ResponseEntity<List<Produto>> findAllProduto() {
		List<Produto> produtoList = produtoService.findAllProduto();

		if (produtoList.isEmpty()) {
			throw new NoSuchElementFoundException("Nenhum produto encontrado.");
		} else {
			return new ResponseEntity<>(produtoList, HttpStatus.OK);
		}
	}

	@GetMapping("/{id}")
	@Operation(summary = "Busca um produto cadastrado através do seu ID.", parameters = {
			@Parameter(name = "id", description = "Id do produto desejado.") }, responses = {
					@ApiResponse(responseCode = "200", description = "Sucesso. Retorna o produto desejado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Produto.class))),
					@ApiResponse(responseCode = "404", description = "Falha. Não há um produto cadastrado com o ID fornecido.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
					@ApiResponse(responseCode = "500", description = "Falha. Erro inesperado.", content = @Content) })
	public ResponseEntity<Produto> findProdutoById(@PathVariable Integer id) {
		Produto produto = produtoService.findProdutoById(id);
		if (produto == null) {
			throw new NoSuchElementFoundException("O Produto de id = " + id + " não foi encontrado.");
		} else {
			return new ResponseEntity<>(produto, HttpStatus.OK);
		}
	}

	@GetMapping("/dto/{id}")
	@Operation(summary = "Busca um produto cadastrado através do seu ID.", parameters = {
			@Parameter(name = "id", description = "Id do produto desejado.") }, responses = {
					@ApiResponse(responseCode = "200", description = "Sucesso. Retorna o produto desejado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoDTO.class))),
					@ApiResponse(responseCode = "404", description = "Falha. Não há um produto cadastrado com o ID fornecido.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
					@ApiResponse(responseCode = "500", description = "Falha. Erro inesperado.", content = @Content) })
	public ResponseEntity<ProdutoDTO> findProdutoDTOById(@PathVariable Integer id) {
		ProdutoDTO produtoDTO = produtoService.findProdutoDTOById(id);

		if (produtoDTO == null) {
			throw new NoSuchElementFoundException("O Produto de id = " + id + " não foi encontrado.");
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
	@Operation(summary = "Cadastra um novo produto.", responses = {
			@ApiResponse(responseCode = "200", description = "Sucesso. Cadastra um novo produto.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Produto.class))),
			@ApiResponse(responseCode = "500", description = "Falha. Erro inesperado.", content = @Content) })
	public ResponseEntity<Produto> saveProduto(@Valid @RequestBody Produto produto) {
		return new ResponseEntity<>(produtoService.saveProduto(produto), HttpStatus.CREATED);
	}

	@PostMapping("/dto")
	@Operation(summary = "Cadastra um novo produto.", responses = {
			@ApiResponse(responseCode = "200", description = "Sucesso. Cadastra um novo produto.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoDTO.class))),
			@ApiResponse(responseCode = "500", description = "Falha. Erro inesperado.", content = @Content) })
	public ResponseEntity<ProdutoDTO> saveProdutoDTO(@Valid @RequestBody ProdutoDTO produtoDTO) {
		return new ResponseEntity<>(produtoService.saveProdutoDTO(produtoDTO), HttpStatus.CREATED);
	}

	@PutMapping
	@Operation(summary = "Atualiza um produto cadastrado.", responses = {
			@ApiResponse(responseCode = "200", description = "Sucesso. Atualiza o produto desejado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Produto.class))),
			@ApiResponse(responseCode = "404", description = "Falha. Não há um produto com o ID fornecido.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Falha. Erro inesperado.", content = @Content) })
	public ResponseEntity<Produto> updateProduto(@Valid @RequestBody Produto produto) {
		if (produtoService.findProdutoById(produto.getIdProduto()) == null) {
			throw new NoSuchElementFoundException(
					"Não foi possível atualizar. O Produto de id = " + produto.getIdProduto() + " não foi encontrado.");
		}

		return new ResponseEntity<>(produtoService.updateProduto(produto), HttpStatus.OK);
	}

	@DeleteMapping
	@Operation(summary = "Exclui um produto cadastrado.", responses = {
			@ApiResponse(responseCode = "200", description = "Sucesso. Exclui o produto desejado.", content = @Content),
			@ApiResponse(responseCode = "404", description = "Falha. Não há um produto com o ID fornecido.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Falha. Erro inesperado.", content = @Content) })
	public ResponseEntity<String> deletePoduto(@RequestBody Produto produto) {
		if (produtoService.findProdutoById(produto.getIdProduto()) == null) {
			throw new NoSuchElementFoundException(
					"Não foi possível excluir. O Produto de id = " + produto.getIdProduto() + " não foi encontrado.");
		}

		produtoService.deleteProduto(produto);
		return new ResponseEntity<>("O Produto de id = " + produto.getIdProduto() + " foi excluído com sucesso.",
				HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Exclui um produto cadastrado através do seu ID.", responses = {
			@ApiResponse(responseCode = "200", description = "Sucesso. Exclui o produto desejado.", content = @Content),
			@ApiResponse(responseCode = "404", description = "Falha. Não há um produto com o ID fornecido.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Falha. Erro inesperado.", content = @Content) })
	public ResponseEntity<String> deleteProdutoById(@PathVariable Integer id) {
		if (produtoService.findProdutoById(id) == null) {
			throw new NoSuchElementFoundException(
					"Não foi possível excluir. O Produto de id = " + id + " não foi encontrado.");
		}

		produtoService.deleteProdutoById(id);
		return new ResponseEntity<>("O Produto de id = " + id + " foi excluído com sucesso.", HttpStatus.OK);
	}

}
