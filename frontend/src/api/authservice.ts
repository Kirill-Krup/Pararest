import type {RegisterRequest, JwtResponse, LoginRequest} from '../models/auth.types.ts';
import type { ErrorResponse } from '../models/ErrorResponse.ts';

const API_BASE_URL = 'http://localhost:8080/api/v1/auth';

class AuthService {
    async register(data: RegisterRequest): Promise<JwtResponse> {
        try {
            const response = await fetch(`${API_BASE_URL}/register`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',        
                },
                body: JSON.stringify(data)
            });
            
            if (!response.ok) {
                const error: ErrorResponse = await response.json();
                throw new Error(error.message || 'Ошибка регистрации');
            }
            
            const result: JwtResponse = await response.json();
            
            if (result.accessToken) {
                document.cookie = `auth_token=${result.accessToken}; path=/; max-age=86400`;
            }
            
            return result;
            
        } catch (error) {
            console.error('Registration error: ', error);
            throw error;
        }
    }

    async login(data: LoginRequest): Promise<JwtResponse>{
        try{
            const response = await fetch(`${API_BASE_URL}/login`,{
                method: 'POST',
                headers:{
                    'Content-Type': 'application/json',
                },
                body:JSON.stringify(data)
            })
            if (!response.ok) {
                const error: ErrorResponse = await response.json();
                throw new Error(error.message || 'Ошибка входа');
            }const result: JwtResponse = await response.json();

            if (result.accessToken) {
                document.cookie = `auth_token=${result.accessToken}; path=/; max-age=86400`;
            }

            return result;

        } catch (error) {
            console.error('Login error: ', error);
            throw error;
        }
    }

    logout(): void {
        document.cookie = 'auth_token=; path=/; max-age=0';
    }
}

export default new AuthService();