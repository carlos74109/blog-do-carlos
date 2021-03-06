package com.spring.codeBlog.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.codeBlog.model.Post;
import com.spring.codeBlog.service.CodeBlogService;

@Controller
public class CodeBlogController {
	
	@Autowired
	CodeBlogService codeBlogService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getPost() {
		ModelAndView mv = new ModelAndView("posts");
		List<Post> posts = codeBlogService.findAll();
		mv.addObject("posts", posts);
		return mv;
	}
	
	
	@RequestMapping(value = "/posts/{id}", method = RequestMethod.GET)
	public ModelAndView getPostDetalhe(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("postdetlhado");
		Post post = codeBlogService.findById(id);
		mv.addObject("post", post);
		return mv;
	}
	
	
	@RequestMapping(value = "/novopost", method = RequestMethod.GET)
	public String getPostForm() {
		return "postForm";
	}
	
	
	@RequestMapping(value = "/novopost", method = RequestMethod.POST)
	public String savePost(@Valid Post post, BindingResult bindingResult, RedirectAttributes redirectAtribute) {
//		if(bindingResult.hasErrors()) {
//			redirectAtribute.addFlashAttribute("mensagem", "Verifiquem se os campos estão preenchidos");
//			return "redirect:/novopost";
//		}
		if(post.getAutor().isEmpty()) {
			redirectAtribute.addFlashAttribute("mensagem", "Por favor, preencha o campo do autor");
			return "redirect:/novopost";
		}else if(post.getTitulo().isEmpty()) {
			redirectAtribute.addFlashAttribute("mensagem", "Por favor, preencha o campo de titulo");
			return "redirect:/novopost";
		}else if(post.getTexto().isEmpty()) {
			redirectAtribute.addFlashAttribute("mensagem", "Por favor, preencha o texto");
			return "redirect:/novopost";
		}
		post.setData(LocalDate.now());
		codeBlogService.save(post);
		return "redirect:/";
	}
	
	
	@RequestMapping(value = "/deletepost/{id}", method = RequestMethod.POST)
	public String deletePost(@PathVariable Long id, RedirectAttributes redirectAtribute) {
		codeBlogService.delete(id);
		redirectAtribute.addFlashAttribute("mensagemApagar", "Dado apagado com sucesso");
		return "redirect:/";
	}
	
	
	
   @RequestMapping(value ="/editpost/{id}", method = RequestMethod.GET)
	 public ModelAndView editPost(@PathVariable long id) {
	   	ModelAndView mv = new ModelAndView("editarpost");
		Post post = codeBlogService.findById(id);
		mv.addObject("post", post);
		
	    return mv;
	 }
   
   @RequestMapping(value = "/editpost/{id}", method = RequestMethod.POST)
   public String editar(@PathVariable Long id, Post post, RedirectAttributes redirectAtribute) {  
       codeBlogService.save(post);
       redirectAtribute.addFlashAttribute("mensagemEditar", "Post editado com sucesso!!!");
       return "redirect:/";
   }
   

	
}
