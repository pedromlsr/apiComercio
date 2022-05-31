package com.residencia.comercio.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.residencia.comercio.dtos.CategoriaDTO;
import com.residencia.comercio.entities.Categoria;
import com.residencia.comercio.exceptions.ErrorResponse;
import com.residencia.comercio.exceptions.NoSuchElementFoundException;
import com.residencia.comercio.services.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
	@Autowired
	CategoriaService categoriaService;

	@GetMapping
	@Operation(summary = "Busca todas as categorias cadastradas.", responses = {
			@ApiResponse(responseCode = "200", description = "Sucesso. Retorna todas as categorias cadastradas.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))),
			@ApiResponse(responseCode = "404", description = "Falha. Nenhuma categoria encontrada.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Falha. Erro inesperado.", content = @Content) })
	public ResponseEntity<List<Categoria>> findAllCategoria() {
		List<Categoria> categoriaList = categoriaService.findAllCategoria();

		if (categoriaList.isEmpty()) {
			throw new NoSuchElementFoundException("Nenhuma categoria encontrada.");
		} else {
			return new ResponseEntity<>(categoriaList, HttpStatus.OK);
		}
	}

	@GetMapping("/{id}")
	@Operation(summary = "Busca uma categoria cadastrada através do seu ID.", parameters = {
			@Parameter(name = "id", description = "Id da categoria desejada.") }, responses = {
					@ApiResponse(responseCode = "200", description = "Sucesso. Retorna a categoria desejada.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))),
					@ApiResponse(responseCode = "404", description = "Falha. Não há uma categoria cadastrada com o ID fornecido.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
					@ApiResponse(responseCode = "500", description = "Falha. Erro inesperado.", content = @Content) })
	public ResponseEntity<Categoria> findCategoriaById(@PathVariable Integer id) {
		Categoria categoria = categoriaService.findCategoriaById(id);
		if (categoria == null) {
			throw new NoSuchElementFoundException("A Categoria de id = " + id + " não foi encontrada.");
		}

		return new ResponseEntity<>(categoria, HttpStatus.OK);
	}

	@GetMapping("/dto/{id}")
	@Operation(summary = "Busca uma categoria cadastrada através do seu ID.", parameters = {
			@Parameter(name = "id", description = "Id da categoria desejada.") }, responses = {
					@ApiResponse(responseCode = "200", description = "Sucesso. Retorna a categoria desejada.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoriaDTO.class))),
					@ApiResponse(responseCode = "404", description = "Falha. Não há uma categoria cadastrada com o ID fornecido.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
					@ApiResponse(responseCode = "500", description = "Falha. Erro inesperado.", content = @Content) })
	public ResponseEntity<CategoriaDTO> findCategoriaDTOById(@PathVariable Integer id) {
		CategoriaDTO categoriaDto = categoriaService.findCategoriaDTOById(id);
		if (categoriaDto == null) {
			throw new NoSuchElementFoundException("A Categoria de id = " + id + " não foi encontrada.");
		}

		return new ResponseEntity<>(categoriaDto, HttpStatus.OK);
	}

	@PostMapping
	@Operation(summary = "Cadastra uma nova categoria.", responses = {
			@ApiResponse(responseCode = "200", description = "Sucesso. Cadastra uma nova categoria.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))),
			@ApiResponse(responseCode = "500", description = "Falha. Erro inesperado.", content = @Content) })
	public ResponseEntity<Categoria> saveCategoria(@Valid @RequestBody Categoria categoria) {
		return new ResponseEntity<>(categoriaService.saveCategoria(categoria), HttpStatus.CREATED);
	}

	@PostMapping("/dto")
	@Operation(summary = "Cadastra uma nova categoria.", responses = {
			@ApiResponse(responseCode = "200", description = "Sucesso. Cadastra uma nova categoria.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoriaDTO.class))),
			@ApiResponse(responseCode = "500", description = "Falha. Erro inesperado.", content = @Content) })
	public ResponseEntity<CategoriaDTO> saveCategoriaDTO(@Valid @RequestBody CategoriaDTO categoriaDTO) {
		return new ResponseEntity<>(categoriaService.saveCategoriaDTO(categoriaDTO), HttpStatus.CREATED);
	}

	@PostMapping(value = "/com-foto", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<Categoria> saveCategoriaComFoto(@RequestPart("categoria") String categoria,
			@RequestPart("file") MultipartFile file) throws Exception {
		return new ResponseEntity<>(categoriaService.saveCategoriaComFoto(categoria, file), HttpStatus.CREATED);
	}

	@PutMapping
	@Operation(summary = "Atualiza uma categoria cadastrada.", responses = {
			@ApiResponse(responseCode = "200", description = "Sucesso. Atualiza a categoria desejada.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))),
			@ApiResponse(responseCode = "404", description = "Falha. Não há uma categoria com o ID fornecido.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Falha. Erro inesperado.", content = @Content) })
	public ResponseEntity<Categoria> updateCategoria(@Valid @RequestBody Categoria categoria) {
		if (categoriaService.findCategoriaById(categoria.getIdCategoria()) == null) {
			throw new NoSuchElementFoundException(
					"Não foi possível atualizar. A Categoria de id = " + categoria.getIdCategoria() + " não foi encontrada.");
		}

		return new ResponseEntity<>(categoriaService.updateCategoria(categoria), HttpStatus.OK);
	}

	@DeleteMapping
	@Operation(summary = "Exclui uma categoria cadastrada.", responses = {
			@ApiResponse(responseCode = "200", description = "Sucesso. Exclui a categoria desejada.", content = @Content),
			@ApiResponse(responseCode = "404", description = "Falha. Não há uma categoria com o ID fornecido.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Falha. Erro inesperado.", content = @Content) })
	public ResponseEntity<String> deleteCategoria(@RequestBody Categoria categoria) {
		if (categoriaService.findCategoriaById(categoria.getIdCategoria()) == null) {
			throw new NoSuchElementFoundException(
					"Não foi possível excluir. A Categoria de id = " + categoria.getIdCategoria() + " não foi encontrada.");
		}

		categoriaService.deleteCategoria(categoria);
		return new ResponseEntity<>("A Categoria de id = " + categoria.getIdCategoria() + " foi excluída com sucesso.", HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Exclui uma categoria cadastrada através do seu ID.", responses = {
			@ApiResponse(responseCode = "200", description = "Sucesso. Exclui a categoria desejada.", content = @Content),
			@ApiResponse(responseCode = "404", description = "Falha. Não há uma categoria com o ID fornecido.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Falha. Erro inesperado.", content = @Content) })
	public ResponseEntity<String> deleteCategoriaById(@PathVariable Integer id) {
		if (categoriaService.findCategoriaById(id) == null) {
			throw new NoSuchElementFoundException(
					"Não foi possível excluir. A Categoria de id = " + id + " não foi encontrada.");
		}

		categoriaService.deleteCategoriaById(id);
		return new ResponseEntity<>("A Categoria de id = " + id + " foi excluída com sucesso.", HttpStatus.OK);
	}

}
