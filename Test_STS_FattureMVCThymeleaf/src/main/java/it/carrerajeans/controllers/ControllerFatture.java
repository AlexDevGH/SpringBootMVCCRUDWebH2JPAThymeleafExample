package it.carrerajeans.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.carrerajeans.entities.Fattura;
import it.carrerajeans.excp.FatturaNonTrovataException;
import it.carrerajeans.pdfview.InvoiceDataPdfExport;
import it.carrerajeans.services.IFattureService;

@Controller
@RequestMapping("/fatture")
public class ControllerFatture {
	
	@Autowired
	IFattureService  ifs; 

	public ControllerFatture() {					
	}
	
	@GetMapping({"/",  ""})
	public String homePage() {
		return "homepage"; 
	}
	
	@GetMapping("/getAllInvoices")
	public String getAllFatture(@RequestParam(value = "message", required = false) String message, Model model) {
		List<Fattura> listaF = new ArrayList<Fattura>();
		listaF = ifs.getAllFatture();
		model.addAttribute("list", listaF);
		model.addAttribute("message", message);
		return "tuttelefatture"; 
	}
	
	@GetMapping("/delete")
	public String cancellaFattura(@RequestParam Long id, RedirectAttributes att) {
		try {
			ifs.deleteFatturaById(id);
			att.addAttribute("message", "Cancellata fattura n° " + id);
		}
		catch(FatturaNonTrovataException e) {
			att.addAttribute("message", e.getMessage());
		}
		
		return "redirect:getAllInvoices"; 
	}
	
	@GetMapping("/register")
	public String aggFattura() {
		return "registrafattura"; 
	}
	
	
	@PostMapping("/save")
	public String saveFattura(@ModelAttribute Fattura f, Model model) {		
		Long id = ifs.salvaFattura(f).getId(); 
		String msg = "Inserita fattura n° " + id; 
		model.addAttribute("message", msg);		
		return "registrafattura"; 
	}
	
	@GetMapping("/edit")
	public String modFattura(@RequestParam Long id, Model model, RedirectAttributes att) {
		String pagina = null;
		try {
			Fattura f = ifs.getFatturaById(id);
			model.addAttribute("fattura", f);
			pagina = "editfattura";
		}
		catch(FatturaNonTrovataException e) {
			att.addAttribute("message", e.getMessage());
			pagina = "redirect:getAllInvoices"; 
		}
		
		return pagina; 		
	}
	
	@PostMapping("/update")
	public String mod2Fattura(@ModelAttribute Fattura f, RedirectAttributes att) {
		ifs.updateFattura(f);
		Long id = f.getId(); 
		att.addAttribute("message", "Fattura n° " + id + " aggiornata correttamente. ");
		return "redirect:getAllInvoices"; 
	}
	
	/**
	 * GENERAZIONE PDF DFELLE FATTURE
	 * @return
	 */
	@GetMapping("/pdf")
	public ModelAndView export2PDF() {
		ModelAndView mav = new ModelAndView(); 
		mav.setView(new InvoiceDataPdfExport());
		List<Fattura> listaF = ifs.getAllFatture();
		mav.addObject("list", listaF);
		return mav; 
		
	}

}
