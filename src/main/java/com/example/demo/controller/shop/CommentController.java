package com.example.demo.controller.shop;


import com.example.demo.entity.Comment;
import com.example.demo.entity.User;
import com.example.demo.model.request.CreateCommentPostRequest;
import com.example.demo.model.request.CreateCommentProductRequest;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/api/comments/post")
    public ResponseEntity<Object> createComment(@Valid @RequestBody CreateCommentPostRequest createCommentPostRequest){
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Comment comment = commentService.createCommentPost(createCommentPostRequest, user.getId());
        return ResponseEntity.ok(comment);
    }

    @PostMapping("/api/comments/product")
    public ResponseEntity<Object> createComment(@Valid @RequestBody CreateCommentProductRequest createCommentProductRequest){
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Comment comment = commentService.createCommentProduct(createCommentProductRequest, user.getId());
        return ResponseEntity.ok(comment);
    }

}
