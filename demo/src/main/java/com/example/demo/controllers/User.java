package com.example.demo.controllers;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class User {
	public static boolean isClient = true;
	public static int id = 0;
}
