package com.residencia.comercio.dtos;

public class EmpresaDTO {
	private String cnpj;
	private String tipo;
	private String nome;
	private String telefone;
	private String email;
	private String fantasia;
	private String situacao;
	private String abertura;
	private String numero;
//	private String porte;
//	private String data_situacao;
//	private String motivo_situacao;
//	private String natureza_juridica;
//	private String ultima_atualizacao;
//	private String status;
//	private String logradouro;
//	private String complemento;
//	private String cep;
//	private String bairro;
//	private String municipio;
//	private String uf;

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getAbertura() {
		return abertura;
	}

	public void setAbertura(String abertura) {
		this.abertura = abertura;
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

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getFantasia() {
		return fantasia;
	}

	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

}
