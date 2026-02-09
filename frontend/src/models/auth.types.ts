import type { UserDTO } from "./UserDTO.ts";

export interface JwtResponse{
    accessToken:string,
    refreshToken:string
}


export interface RegisterRequest{
    password:string,
    rememberMe:boolean,
    newUser:UserDTO
}

export interface LoginRequest{
    login: string,
    password: string,
    rememberMe: boolean
}