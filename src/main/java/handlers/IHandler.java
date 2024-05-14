package handlers;

import java.io.BufferedReader;

import java.io.BufferedWriter;

import java.io.IOException;

public interface IHandler {

    int handle(BufferedReader reader, BufferedWriter writer) throws IOException;

}