package com.astrodiary.backend.controller;
import com.astrodiary.backend.entity.Post;
import com.astrodiary.backend.repository.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.astrodiary.backend.entity.User;
import com.astrodiary.backend.repository.UserRepository;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*")

public class PostController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


     @GetMapping("/user/{username}")
    public List<Post> getPostsByUsername(@PathVariable String username) {
    List<Post> sviPostovi = postRepository.findAll();
    List<Post> filtriraniPostovi = new java.util.ArrayList<>();
    
    for (Post p : sviPostovi) {
        if (p.getUser() != null && p.getUser().getUsername().equals(username)) {
            
            Post cistPost = new Post();
            cistPost.setId(p.getId());
            cistPost.setNaslov(p.getNaslov());
            cistPost.setTekst(p.getTekst());
            cistPost.setDatumObjave(p.getDatumObjave());
            
            cistPost.setUser(null); 
            
            filtriraniPostovi.add(cistPost);
        }
    }
    
    System.out.println("--- FILTTRIRANJE ---");
    System.out.println("Na sajtu ima ovoliko postova: " + filtriraniPostovi.size());
    
    return filtriraniPostovi;
}


   @GetMapping("/{id}") //uzima jedan post po id-u (za editovanje)
    public Post getPostById(@PathVariable Long id) {
    Post p = postRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Post sa ID-jem " + id + " ne postoji!"));

   //dodato da bi se izbegla beskonacna petlja
    Post cistPost = new Post();
    cistPost.setId(p.getId());
    cistPost.setNaslov(p.getNaslov());
    cistPost.setTekst(p.getTekst());
    cistPost.setDatumObjave(p.getDatumObjave());
    
    
    cistPost.setUser(null); 

    return cistPost;
}


   @PostMapping("/create/{username}") //create-dodaje novi post
    public Post createPost(@PathVariable String username, @RequestBody Post noviPost) {
       
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Korisnik nije pronađen!"));
        
        noviPost.setUser(user);
        
        noviPost.setDatumObjave(java.time.LocalDateTime.now());
    
       System.out.println("--- KREIRANJE OBjAVE ---");
       System.out.println("Čuvam post za korisnika: " + username);
        
        return postRepository.save(noviPost);
    }

    @DeleteMapping("/{id}") //delete-briše post po id-u
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
        return ResponseEntity.ok().build(); 
    }

    @PutMapping("/update/{id}") //update -izmenjen post po id-u
    public Post updatePost(@PathVariable Long id, @RequestBody Post postDetails) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Post sa ID-jem " + id + " ne postoji!"));
            
        post.setNaslov(postDetails.getNaslov());
        post.setTekst(postDetails.getTekst());
        
       
        post.setDatumObjave(java.time.LocalDateTime.now());
        
        System.out.println("--- UPDATE OBjAVE ---");
        System.out.println("Uspešno izmenjen post sa ID-jem: " + id);
        
        return postRepository.save(post);
    }
    
}
