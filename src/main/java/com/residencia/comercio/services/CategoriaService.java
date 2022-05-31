package com.residencia.comercio.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.residencia.comercio.dtos.CategoriaDTO;
import com.residencia.comercio.entities.Categoria;
import com.residencia.comercio.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	@Autowired
	CategoriaRepository categoriaRepository;

	@Autowired
	ArquivoService arquivoService;

	@Autowired
	EmailService emailService;

	public List<Categoria> findAllCategoria() {
		return categoriaRepository.findAll();
	}

	public Categoria findCategoriaById(Integer id) {
		return categoriaRepository.existsById(id) ? categoriaRepository.findById(id).get() : null;
	}

	public CategoriaDTO findCategoriaDTOById(Integer id) {
		return categoriaRepository.existsById(id) ? convertEntityToDto(categoriaRepository.findById(id).get()) : null;
	}

	public Categoria saveCategoria(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}

	public CategoriaDTO saveCategoriaDTO(CategoriaDTO categoriaDTO) {
		return convertEntityToDto(categoriaRepository.save(convertDtoToEntity(categoriaDTO)));
	}

	public Categoria saveCategoriaComFoto(String categoriaString, MultipartFile file) throws Exception {

		Categoria categoriaConvertida = new Categoria();

		try {
			ObjectMapper objMapper = new ObjectMapper();
			categoriaConvertida = objMapper.readValue(categoriaString, Categoria.class);
		} catch (IOException e) {
			System.out.println("Ocorreu um erro na conversão.");
		}

		Categoria categoriaBD = categoriaRepository.save(categoriaConvertida);

		categoriaBD.setNomeImagem(file.getOriginalFilename() + "_" + categoriaBD.getIdCategoria());

		Categoria categoriaAtualizada = categoriaRepository.save(categoriaBD);

//		Chamando o método que fará a cópia do arquivo para a pasta definida
		try {
			arquivoService.criarArquivo(categoriaBD.getIdCategoria() + "_" + file.getOriginalFilename(), file);
		} catch (Exception e) {
			throw new Exception("Ocorreu um erro ao tentar copiar o arquivo - " + e.getStackTrace());
		}

//		Cuidado para definir um endereço válido quando for fazer de verdade
		String corpoEmail = "Foi cadastrada uma nova categoria:" + categoriaAtualizada.toString();
		emailService.enviarEmailTexto("teste@teste.com", "Cadastro de Categoria", corpoEmail);
		emailService.enviarEmailHtml(file, "teste@teste.com", "Cadastro de Categoria", corpoEmail);

		return categoriaAtualizada;
	}

	public Categoria updateCategoria(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}

	public void deleteCategoria(Categoria categoria) {
		categoriaRepository.delete(categoria);
	}

	public void deleteCategoriaById(Integer id) {
		categoriaRepository.deleteById(id);
	}

	private Categoria convertDtoToEntity(CategoriaDTO categoriaDTO) {
		Categoria categoria = new Categoria();

		categoria.setIdCategoria(categoriaDTO.getIdCategoria());
		categoria.setNomeCategoria(categoriaDTO.getNomeCategoria());

		return categoria;
	}

	private CategoriaDTO convertEntityToDto(Categoria categoria) {
		CategoriaDTO categoriaDTO = new CategoriaDTO();

		categoriaDTO.setIdCategoria(categoria.getIdCategoria());
		categoriaDTO.setNomeCategoria(categoria.getNomeCategoria());

		return categoriaDTO;
	}
}
