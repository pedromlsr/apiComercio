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
	
	public List<Categoria> findAllCategoria(){
		return categoriaRepository.findAll();
	}
	
	public Categoria findCategoriaById(Integer id) {
		return categoriaRepository.findById(id).isPresent() ?
				categoriaRepository.findById(id).get() : null;
	}

	public CategoriaDTO findCategoriaDTOById(Integer id) {
		Categoria categoria = categoriaRepository.findById(id).isPresent() ?
				categoriaRepository.findById(id).get() : null;
		
		CategoriaDTO categoriaDTO = new CategoriaDTO();
		if(null != categoria) {
			categoriaDTO = converterEntidadeParaDto(categoria);
		}
		return categoriaDTO;
	}
	
	public Categoria saveCategoria(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}
	
	public CategoriaDTO saveCategoriaDTO(CategoriaDTO categoriaDTO) {
			
		Categoria categoria = new Categoria();
		
		categoria.setIdCategoria(categoriaDTO.getIdCategoria());
		Categoria novoCategoria = categoriaRepository.save(categoria);
		
		return converterEntidadeParaDto(novoCategoria);
	}
	
	public Categoria saveCategoriaComFoto(String categoriaString, MultipartFile file) {
		
		Categoria categoriaConvertida = new Categoria();
		
		try {
			ObjectMapper objMapper = new ObjectMapper();
			categoriaConvertida = objMapper.readValue(categoriaString, Categoria.class);
		} catch(IOException e) {
			System.out.println("Ocorreu um erro na conversão.");
		}
		
		
//		while (null!=categoriaRepository.existsNomeImagem(categoriaConvertida.getNomeImagem())) {
//            categoriaConvertida.setNomeImagem(categoriaConvertida.getNomeImagem());
//        } else {
//        	categoriaConvertida.set
//        }
		
//		if (categoriaRepository.existsByNomeImagem(file.getOriginalFilename()) == false) {
//			Categoria categoriaBD = categoriaRepository.save(categoriaConvertida);
//		} else {
//			categoriaConvertida.setNomeImagem(file.getOriginalFilename() + );
//		}
		
//		if (categoriaConvertida.getNomeImagem().equals(categoriaRepository.findByNomeImagem(file.getOriginalFilename()))) {
//			
//		}

		Categoria categoriaBD = categoriaRepository.save(categoriaConvertida);
		
		categoriaBD.setNomeImagem(categoriaBD.getIdCategoria() + "_" + file.getOriginalFilename());
		
		Categoria categoriaAtualizada = categoriaRepository.save(categoriaBD);
		
		return null;
	}
	
	public Categoria updateCategoria(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}
	
	public void deleteCategoria(Integer id) {
		Categoria inst = categoriaRepository.findById(id).get();
		categoriaRepository.delete(inst);
	}
	
	public void deleteCategoria(Categoria categoria) {
		categoriaRepository.delete(categoria);
	}
	
	private Categoria convertDTOToEntidade(CategoriaDTO categoriaDTO){
		Categoria categoria = new Categoria();
		
		categoria.setIdCategoria(categoriaDTO.getIdCategoria());
		return categoria;
	}
		
	private CategoriaDTO converterEntidadeParaDto(Categoria categoria) {
		CategoriaDTO categoriaDTO = new CategoriaDTO();
		categoriaDTO.setIdCategoria(categoria.getIdCategoria());
		return categoriaDTO;
	}
}
