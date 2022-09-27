package com.broadcastsocket.web.Controller;

import com.broadcastsocket.web.Dto.TokenReq;
import com.broadcastsocket.web.Dto.TokenRes;
import com.broadcastsocket.web.Security.JwtUtil;
import com.broadcastsocket.web.Service.CostumUserdetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CostumUserdetailService costumUserdetailService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signIn")
    public ResponseEntity<?> generateToken(@RequestBody TokenReq dto) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUserName(),dto.getPassword()));
        }catch(UsernameNotFoundException e){
            throw  new Exception("Bed credentials");
        }
        UserDetails userDetails = this.costumUserdetailService.loadUserByUsername(dto.getUserName());

        String token = this.jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new TokenRes(token));
    }
}
