package com.residencia.comercio.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.residencia.comercio.dtos.EmpresaDTO;
import com.residencia.comercio.dtos.EnderecoDTO;
import com.residencia.comercio.dtos.FornecedorDTO;
import com.residencia.comercio.entities.Fornecedor;
import com.residencia.comercio.repositories.FornecedorRepository;

@Service
public class FornecedorService {
	@Autowired
	FornecedorRepository fornecedorRepository;

	public List<Fornecedor> findAllFornecedor() {
		return fornecedorRepository.findAll();
	}

	public Fornecedor findFornecedorById(Integer id) {
		return fornecedorRepository.existsById(id) ? fornecedorRepository.findById(id).get() : null;
	}

	public FornecedorDTO findFornecedorDTOById(Integer id) {
		return fornecedorRepository.existsById(id) ? convertEntityToDTO(fornecedorRepository.findById(id).get()) : null;
	}

	public EmpresaDTO consultarDadosPorCnpj(String cnpj) {
		RestTemplate restTemplate = new RestTemplate();
		String uri = "https://www.receitaws.com.br/v1/cnpj/{cnpj}";
		Map<String, String> params = new HashMap<String, String>();
		params.put("cnpj", cnpj);

		return restTemplate.getForObject(uri, EmpresaDTO.class, params);
	}

	public EnderecoDTO consultarEnderecoPorCep(String cep) {
		RestTemplate restTemplate = new RestTemplate();
		String uri = "https://viacep.com.br/ws/{cep}/json/";
		Map<String, String> params = new HashMap<String, String>();
		params.put("cep", cep);

		return restTemplate.getForObject(uri, EnderecoDTO.class, params);
	}

	public Fornecedor saveFornecedor(Fornecedor fornecedor) {
		return fornecedorRepository.save(fornecedor);
	}

	public FornecedorDTO saveFornecedorDTO(FornecedorDTO fornecedorDTO) {
		return convertEntityToDTO(fornecedorRepository.save(convertDTOToEntity(fornecedorDTO)));
	}

	public FornecedorDTO saveFornecedorByCnpj(String cnpj) {
		return convertEntityToDTO(fornecedorRepository.save(setEmpresaDTOToFornecedor(consultarDadosPorCnpj(cnpj))));
	}

	public FornecedorDTO saveFornecedorByCnpjCep(String cnpj, String cep) {
		Fornecedor fornecedorSemEndereco = setEmpresaDTOToFornecedor(consultarDadosPorCnpj(cnpj));
		Fornecedor fornecedorCompleto = setEnderecoDTOToFornecedor(fornecedorSemEndereco, consultarEnderecoPorCep(cep));
		return convertEntityToDTO(fornecedorRepository.save(fornecedorCompleto));
	}

	public Fornecedor updateFornecedor(Fornecedor fornecedor) {
		return fornecedorRepository.save(fornecedor);
	}

	public FornecedorDTO updateFornecedorByCep(Integer id, String cep) {
		return convertEntityToDTO(fornecedorRepository.save(
				setEnderecoDTOToFornecedor(fornecedorRepository.findById(id).get(), consultarEnderecoPorCep(cep))));
	}

	public void deleteFornecedor(Fornecedor fornecedor) {
		fornecedorRepository.delete(fornecedor);
	}

	public void deleteFornecedorById(Integer id) {
		fornecedorRepository.deleteById(id);
	}

	private Fornecedor convertDTOToEntity(FornecedorDTO fornecedorDTO) {
		Fornecedor fornecedor = new Fornecedor();

		fornecedor.setIdFornecedor(fornecedorDTO.getIdFornecedor());
		fornecedor.setCnpj(fornecedorDTO.getCnpj());
		fornecedor.setTipo(fornecedorDTO.getTipo());
		fornecedor.setRazaoSocial(fornecedorDTO.getRazaoSocial());
		fornecedor.setUf(fornecedorDTO.getUf());
		fornecedor.setTelefone(fornecedorDTO.getTelefone());
		fornecedor.setEmail(fornecedorDTO.getEmail());
		fornecedor.setNomeFantasia(fornecedorDTO.getNomeFantasia());
		fornecedor.setStatusSituacao(fornecedorDTO.getStatusSituacao());
		fornecedor.setBairro(fornecedorDTO.getBairro());
		fornecedor.setLogradouro(fornecedorDTO.getLogradouro());
		fornecedor.setNumero(fornecedorDTO.getNumero());
		fornecedor.setComplemento(fornecedorDTO.getComplemento());
		fornecedor.setCep(fornecedorDTO.getCep());
		fornecedor.setMunicipio(fornecedorDTO.getMunicipio());
		fornecedor.setDataAbertura(fornecedorDTO.getDataAbertura());

		return fornecedor;
	}

	private FornecedorDTO convertEntityToDTO(Fornecedor fornecedor) {
		FornecedorDTO fornecedorDTO = new FornecedorDTO();

		fornecedorDTO.setIdFornecedor(fornecedor.getIdFornecedor());
		fornecedorDTO.setCnpj(fornecedor.getCnpj());
		fornecedorDTO.setTipo(fornecedor.getTipo());
		fornecedorDTO.setRazaoSocial(fornecedor.getRazaoSocial());
		fornecedorDTO.setUf(fornecedor.getUf());
		fornecedorDTO.setTelefone(fornecedor.getTelefone());
		fornecedorDTO.setEmail(fornecedor.getEmail());
		fornecedorDTO.setNomeFantasia(fornecedor.getNomeFantasia());
		fornecedorDTO.setStatusSituacao(fornecedor.getStatusSituacao());
		fornecedorDTO.setBairro(fornecedor.getBairro());
		fornecedorDTO.setLogradouro(fornecedor.getLogradouro());
		fornecedorDTO.setNumero(fornecedor.getNumero());
		fornecedorDTO.setComplemento(fornecedor.getComplemento());
		fornecedorDTO.setCep(fornecedor.getCep());
		fornecedorDTO.setMunicipio(fornecedor.getMunicipio());
		fornecedorDTO.setDataAbertura(fornecedor.getDataAbertura());

		return fornecedorDTO;
	}

	private Fornecedor setEmpresaDTOToFornecedor(EmpresaDTO empresaDTO) {
		Fornecedor fornecedor = new Fornecedor();

		fornecedor.setCnpj(empresaDTO.getCnpj());
		fornecedor.setTipo(empresaDTO.getTipo());
		fornecedor.setRazaoSocial(empresaDTO.getNome());
		fornecedor.setTelefone(empresaDTO.getTelefone());
		fornecedor.setEmail(empresaDTO.getEmail());
		fornecedor.setNomeFantasia(empresaDTO.getFantasia());
		fornecedor.setStatusSituacao(empresaDTO.getSituacao());
		try {
			fornecedor.setDataAbertura(new SimpleDateFormat("dd/MM/yyyy").parse(empresaDTO.getAbertura()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		fornecedor.setNumero(empresaDTO.getNumero());

		return fornecedor;
	}

	private Fornecedor setEnderecoDTOToFornecedor(Fornecedor fornecedor, EnderecoDTO enderecoDTO) {
		fornecedor.setLogradouro(enderecoDTO.getLogradouro());
		fornecedor.setBairro(enderecoDTO.getBairro());
		fornecedor.setComplemento(enderecoDTO.getComplemento());
		fornecedor.setCep(enderecoDTO.getCep());
		fornecedor.setMunicipio(enderecoDTO.getLocalidade());
		fornecedor.setUf(enderecoDTO.getUf());

		return fornecedor;
	}

}
