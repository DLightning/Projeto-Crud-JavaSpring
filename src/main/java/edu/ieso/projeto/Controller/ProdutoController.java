package edu.ieso.projeto.Controller;

import edu.ieso.projeto.Model.Cliente;
import edu.ieso.projeto.Model.Produto;
import edu.ieso.projeto.Repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class ProdutoController {

    @Autowired
    ProdutoRepository produtoRepository;

    //Funcionando
    //Metodo para Criar
    @RequestMapping(value = "/cadastrarProduto", method = RequestMethod.GET)
    public String form(){
        return "page/cadproduto";
    }
    @RequestMapping(value = "/cadastrarProduto", method = RequestMethod.POST)
    public String form(Produto produto){
        produtoRepository.save(produto);
        return "redirect:/";
    }

    //Funcionando
    //Metodo para Listar
    @RequestMapping(value = "/listarProdutos")
    public ModelAndView findAll(){
        ModelAndView mv = new ModelAndView("page/bproduto");
        Iterable<Produto> produtos = produtoRepository.findAll();
        mv.addObject("produtos", produtos);
        return mv;
    }

    //Funcionando
    //Metodo para atualizar
    @GetMapping(value="/editProduto/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid cliente Id:" + id));

        model.addAttribute("produto", produto);
        return "page/update-produto";
    }
    @RequestMapping(value="/updateProduto/{id}", method = RequestMethod.POST)
    public String updateProduto(@PathVariable("id") Long id, Produto produto,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            produto.setId(id);
            return "page/update-produto";
        }

        produtoRepository.save(produto);
        model.addAttribute("produtos", produtoRepository.findAll());
        return "redirect:/listarProdutos";

    }

    //Funcionando
    //Metodo para Deletar
    @RequestMapping(value = {"/deleteProduto/{id}"})
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {

        if(id==null || id == 0){
            attributes.addFlashAttribute("mensagem", "ID não pode ser vazio ou nullo!");
            return "redirect:/listarProdutos";
        }

        Optional<Produto> produto =  produtoRepository.findById(id);
        if(produto.isPresent()){
            produtoRepository.deleteById(id);
            attributes.addFlashAttribute("mensagem", "removido com sucesso!");
        }else{
            attributes.addFlashAttribute("mensagem", " Cliente não existe!");
        }

        return "redirect:/listarProdutos";
    }
}
