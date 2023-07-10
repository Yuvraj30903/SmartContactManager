package com.smart.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.smart.entities.Contact;
import com.smart.entities.SettingRequest;
import com.smart.entities.User;
import com.smart.repo.ContactRepo;
import com.smart.repo.UserRepo;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasRole(\"USER\")")
public class UserController {

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ContactRepo contactRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@ModelAttribute
	public void addcommondata(Model model, Principal principal) {
		User user = userRepo.getUserByUserName(principal.getName());
		model.addAttribute("user", user);
	}

	@GetMapping("/dashboard")
	public String userdashboard(Model model, Principal principal) {
		model.addAttribute("title", "Smart Contact Manager");
		return "user/dashboard";
	}

	@GetMapping("/add_contact")
	public String contact(Model model, HttpSession session) {

		model.addAttribute("title", "Add contact");
		if ((model.getAttribute("msg") == null) || model.getAttribute("type").equals("success"))
			model.addAttribute("contact", new Contact());
		return "user/add_contact";
	}

	@GetMapping("/view_contacts/{page}")
	public String view_contacts(@PathVariable("page") Integer page, Model model, Principal principal,
			HttpSession session) {
		User user = userRepo.getUserByUserName(principal.getName());

		int row = 3;
		Pageable pageable = PageRequest.of(page, row, Sort.by("name"));

		Page<Contact> contacts = contactRepo.findContactByUser(user.getId(), pageable);

		model.addAttribute("row", row);
		model.addAttribute("contacts", contacts);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", contacts.getTotalPages());

		model.addAttribute("title", "My Contacts");
		return "user/view_contacts";
	}

	@PostMapping(value = "/add_contact", consumes = { "multipart/form-data" })
	public String processcontact(Principal principal, @Valid @ModelAttribute("contact") Contact contact,
			BindingResult result, Model model, @RequestParam("contactimage") MultipartFile file,
			RedirectAttributes redirectAttributes) {

		if (!(file.getContentType().startsWith("image"))) {

			FieldError error = new FieldError(result.getObjectName(),"image", "file must be in image format.");
			result.addError(error);

		}
		if (result.hasErrors()) {
			System.out.println(result);
			return "user/add_contact";
		}

		try {

			contact.setImage(file.getOriginalFilename());
			String name = principal.getName();
			User user = userRepo.getUserByUserName(name);
			contact.setUser(user);
			user.getContacts().add(contact);
			userRepo.save(user);

			// saving image

			File saveFile = new ClassPathResource("static/image").getFile();

			Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			// message
			redirectAttributes.addFlashAttribute("msg", "Contact succesfully added!!!!");
			redirectAttributes.addFlashAttribute("type", "success");
		} catch (Exception e) {

			redirectAttributes.addFlashAttribute("contact", contact);
			redirectAttributes.addFlashAttribute("msg", "Something went wrong!!!!");
			redirectAttributes.addFlashAttribute("type", "danger");
			System.out.println("error");
		}

		return "redirect:/user/add_contact";
	}

	@GetMapping("/contact/{cId}")
	public String showContact(@PathVariable("cId") Integer cId, Model model) {
		Optional<Contact> findById = contactRepo.findById(cId);
		Contact contact = findById.get();
		model.addAttribute("contact", contact);
		model.addAttribute("title", contact.getName());
		return "user/user_contact";
	}

	@GetMapping("/contact/delete/{cId}")
	public String deleteContact(@PathVariable("cId") Integer cId, Model model, RedirectAttributes redirectAttributes) {

		Optional<Contact> findById = contactRepo.findById(cId);
		Contact contact = findById.get();
		contactRepo.delete(contact);
		redirectAttributes.addFlashAttribute("msg", "Contact with Id USER" + cId + " deleted successfully!!!");
		return "redirect:/user/view_contacts/0";
	}

	@GetMapping("/contact/update/{cId}")
	public String updateContact(@PathVariable("cId") Integer cId, Model model, RedirectAttributes redirectAttributes) {

		Optional<Contact> findById = contactRepo.findById(cId);
		Contact contact = findById.get();
		model.addAttribute(contact);
		return "user/update_contact";
	}

	@PostMapping(value = "/contact/update/{cId}", consumes = { "multipart/form-data" })
	public String processContact(Principal principal,@Valid @ModelAttribute Contact contact,BindingResult result,
			@RequestParam("contactimage") MultipartFile file, @PathVariable Integer cId, RedirectAttributes r)
	{ 
		if (!(file.getContentType().startsWith("image")) && !file.isEmpty())
		{
			FieldError error=new FieldError(result.getObjectName(), "image", "file must be in image format");
		}
		if(result.hasErrors())
		{ 
			contact.setCid(cId);
			Optional<Contact> findById = contactRepo.findById(cId);
			Contact c = findById.get();
			contact.setImage(c.getImage());
			return "user/update_contact";
		}
		try { 
			contact.setCid(cId);
			String name = principal.getName();
			User user = userRepo.getUserByUserName(name);
			contact.setUser(user); 
			if(!file.isEmpty()) {
				contact.setImage(file.getOriginalFilename());
				File saveFile = new ClassPathResource("static/image").getFile();
				
				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			} 
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		contactRepo.save(contact);
		r.addFlashAttribute("msg", "Contact Updated!!!");
		return "redirect:/user/contact/" + contact.getCid();
	}

	@GetMapping("/profile")
	public String profile(Principal principal, Model model) {
		model.addAttribute("title", "Profile");
		return "user/profile";

	}

	@GetMapping("/setting")
	public String setting(Model model) {
//		model.addAttribute("msg","Just for testing");
		model.addAttribute("title", "Setting");
		return "user/setting";
	}

	@PostMapping("/process_setting")
	public String processSetting(Model m, @ModelAttribute SettingRequest sr, RedirectAttributes r) {
		User user = (User) m.getAttribute("user");
		if (!(passwordEncoder.matches(sr.getOldPassword(), user.getPassword()))) {

			r.addFlashAttribute("msg", "Password doesn't match with given old password!!!");
			r.addFlashAttribute("type", "alert-danger");
			return "redirect:/user/setting";

		}
		if (!(sr.getNewPassword().equals(sr.getConfirmPassword()))) {
			r.addFlashAttribute("msg", "new password and confirm password must be same!!!");
			r.addFlashAttribute("type", "alert-danger");
			return "redirect:/user/setting";
		}
		if (!(sr.getNewPassword().equals(""))) {
			sr.setNewPassword(passwordEncoder.encode(sr.getNewPassword()));
			user.setPassword(sr.getNewPassword());
		}
		user.setName(sr.getName());
		user.setAbout(sr.getAbout());

		userRepo.save(user);
		r.addFlashAttribute("msg", "Setting saved successfully!!!!");
		return "redirect:/user/profile";

	}

}
