package com.residencia.comercio.dtos;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.br.CNPJ;

import com.fasterxml.jackson.annotation.JsonFormat;

public class FornecedorDTO {
	private Integer idFornecedor;

	@NotBlank(message = "O número do CNPJ não pode ficar em branco.")
	@CNPJ(message = "Confira o padrão do CNPJ.")
	private String cnpj;

	private String tipo;

	@NotBlank(message = "A razão social não pode ficar em branco.")
	private String razaoSocial;

	@NotBlank(message = "O telefone não pode ficar em branco.")
	@Pattern(regexp = "([(]?[0-9]{2}[)]?[ ]?[0-9]{4}[-]?[0-9]{4})", message = "Confira o padrão do telefone.")
	private String telefone;

	@NotBlank(message = "O email não pode ficar em branco.")
	@Email(message = "Confira o padrão do email.")
	private String email;

	@NotBlank(message = "O nome fantasia não pode ficar em branco.")
	private String nomeFantasia;

	private String statusSituacao;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dataAbertura;

	@NotBlank(message = "O logradouro não pode ficar em branco.")
	private String logradouro;

	private String numero;

	private String complemento;

	@NotBlank(message = "O bairro não pode ficar em branco.")
	private String bairro;

	@NotBlank(message = "O cep não pode ficar em branco.")
	@Pattern(regexp = "([0-9]{5}[-]?[0-9]{3})", message = "Confira o padrão do CEP.")
	private String cep;

	@NotBlank(message = "O municipio não pode ficar em branco.")
	private String municipio;

	@NotBlank(message = "A UF não pode ficar em branco.")
	@Pattern(regexp = "[a-zA-Z]{2}", message = "Confira o padrão da UF.")
	private String uf;

//	private List<ProdutoDTO> produtoDTOList;

	public Integer getIdFornecedor() {
		return idFornecedor;
	}

	public void setIdFornecedor(Integer idFornecedor) {
		this.idFornecedor = idFornecedor;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getStatusSituacao() {
		return statusSituacao;
	}

	public void setStatusSituacao(String statusSituacao) {
		this.statusSituacao = statusSituacao;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public Date getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

}
