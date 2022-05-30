package com.residencia.comercio.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.residencia.comercio.dtos.EmpresaDTO;
import com.residencia.comercio.dtos.EnderecoDTO;
import com.residencia.comercio.dtos.FornecedorDTO;
import com.residencia.comercio.entities.Fornecedor;
import com.residencia.comercio.exceptions.ErrorResponse;
import com.residencia.comercio.exceptions.NoSuchElementFoundException;
import com.residencia.comercio.services.FornecedorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/fornecedor")
public class FornecedorController {
	@Autowired
	FornecedorService fornecedorService;

	@GetMapping
	@Operation(summary = "Busca todos os fornecedores cadastrados.", responses = {
			@ApiResponse(responseCode = "200", description = "Sucesso. Retorna todos os fornecedores cadastrados.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Fornecedor.class))),
			@ApiResponse(responseCode = "404", description = "Falha. Nenhum fornecedor encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Falha. Erro inesperado.", content = @Content) })
	public ResponseEntity<List<Fornecedor>> findAllFornecedor() {
		List<Fornecedor> fornecedorList = fornecedorService.findAllFornecedor();

		if (fornecedorList.isEmpty()) {
			throw new NoSuchElementFoundException("Nenhum fornecedor encontrado.");
		} else {
			return new ResponseEntity<>(fornecedorList, HttpStatus.OK);
		}
	}

	@GetMapping("/{id}")
	@Operation(summary = "Busca um fornecedor cadastrado através do seu ID.", parameters = {
			@Parameter(name = "id", description = "Id do fornecedor desejado.") }, responses = {
					@ApiResponse(responseCode = "200", description = "Sucesso. Retorna o fornecedor desejado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Fornecedor.class))),
					@ApiResponse(responseCode = "404", description = "Falha. Não há um fornecedor cadastrado com o ID fornecido.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
					@ApiResponse(responseCode = "500", description = "Falha. Erro inesperado.", content = @Content) })
	public ResponseEntity<Fornecedor> findFornecedorById(@PathVariable Integer id) {
		Fornecedor fornecedor = fornecedorService.findFornecedorById(id);
		if (fornecedor == null) {
			throw new NoSuchElementFoundException("O Fornecedor de id = " + id + " não foi encontrado.");
		} else {
			return new ResponseEntity<>(fornecedor, HttpStatus.OK);
		}
	}

	@GetMapping("/dto/{id}")
	public ResponseEntity<FornecedorDTO> findFornecedorDTOById(@PathVariable Integer id) {
		return new ResponseEntity<>(fornecedorService.findFornecedorDTOById(id), HttpStatus.OK);
	}

	@GetMapping("/cnpj/{cnpj}")
	public ResponseEntity<EmpresaDTO> consultarDadosPorCnpj(String cnpj) {
		EmpresaDTO empresaDTO = fornecedorService.consultarDadosPorCnpj(cnpj);
		if (empresaDTO == null) {
			throw new NoSuchElementFoundException("Não foram encontrados dados para o CNPJ informado.");
		} else {
			return new ResponseEntity<>(empresaDTO, HttpStatus.OK);
		}
	}

	@GetMapping("/cep/{cep}")
	public ResponseEntity<EnderecoDTO> consultarEnderecoPorCep(String cep) {
		EnderecoDTO enderecoDTO = fornecedorService.consultarEnderecoPorCep(cep);
		if (enderecoDTO == null) {
			throw new NoSuchElementFoundException("Não foram encontrados dados para o CEP informado.");
		} else {
			return new ResponseEntity<>(enderecoDTO, HttpStatus.OK);
		}
	}

	@PostMapping
	@Operation(summary = "Cadastra um novo fornecedor.", responses = {
			@ApiResponse(responseCode = "200", description = "Sucesso. Cadastra um novo fornecedor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Fornecedor.class))),
			@ApiResponse(responseCode = "500", description = "Falha. Erro inesperado.", content = @Content) })
	public ResponseEntity<Fornecedor> saveFornecedor(@Valid @RequestBody Fornecedor fornecedor) {
		return new ResponseEntity<>(fornecedorService.saveFornecedor(fornecedor), HttpStatus.CREATED);
	}

	@PostMapping("/dto")
	public ResponseEntity<FornecedorDTO> saveFornecedorDTO(@RequestBody FornecedorDTO fornecedorDTO) {
		return new ResponseEntity<>(fornecedorService.saveFornecedorDTO(fornecedorDTO), HttpStatus.CREATED);
	}

	@PostMapping("/cnpj/{cnpj}")
	public ResponseEntity<FornecedorDTO> saveFornecedorCnpj(@PathVariable String cnpj) {
		return new ResponseEntity<>(fornecedorService.saveFornecedorByCnpj(cnpj), HttpStatus.CREATED);
	}

//	@PostMapping("/cnpj")
//	public ResponseEntity<Fornecedor> saveFornecedorCNPJ(@RequestParam String cnpj) {
//		Fornecedor fornecedor = new Fornecedor();
//		Fornecedor novoFornecedor = fornecedorService.saveFornecedor(fornecedor);
//		return new ResponseEntity<>(novoFornecedor, HttpStatus.CREATED);
//	}

	@PutMapping
	public ResponseEntity<Fornecedor> updateFornecedor(@Valid @RequestBody Fornecedor fornecedor) {
		return new ResponseEntity<>(fornecedorService.updateFornecedor(fornecedor), HttpStatus.OK);
	}

	@PutMapping("/cep/{id}/{cep}")
	public ResponseEntity<FornecedorDTO> updateFornecedorByCep(@PathVariable Integer id, @PathVariable String cep) {
		return new ResponseEntity<>(fornecedorService.updateFornecedorByCep(id, cep), HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<String> deleteFornecedor(@RequestBody Fornecedor fornecedor) {
		if (fornecedorService.findFornecedorById(fornecedor.getIdFornecedor()) == null) {
			return new ResponseEntity<>("Não foi possível excluir. O Fornecedor de id = " + fornecedor.getIdFornecedor()
					+ " não foi encontrado.", HttpStatus.NOT_FOUND);
		} else {
			fornecedorService.deleteFornecedor(fornecedor);
			return new ResponseEntity<>(
					"O Fornecedor de id = " + fornecedor.getIdFornecedor() + "foi excluído com sucesso.",
					HttpStatus.OK);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteFornecedorById(@PathVariable Integer id) {
		if (fornecedorService.findFornecedorById(id) == null) {
			return new ResponseEntity<>("Não foi possível excluir. O Fornecedor de id = " + id + " não foi encontrado.",
					HttpStatus.NOT_FOUND);
		} else {
			fornecedorService.deleteFornecedorById(id);
			return new ResponseEntity<>("O Fornecedor de id = " + id + " foi excluído com sucesso.", HttpStatus.OK);
		}
	}

}
