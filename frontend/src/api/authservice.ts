import type { RegisterRequest, JwtResponse, LoginRequest } from '../models/auth.types.ts';
import type { ErrorResponse } from '../models/ErrorResponse.ts';
import type { UserDTO } from '../models/UserDTO.ts';

const API_BASE_URL = 'http://localhost:8080/api/v1/auth';

class AuthService {
    async register(data: RegisterRequest): Promise<JwtResponse> {
        const response = await fetch(`${API_BASE_URL}/register`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });

        if (!response.ok) {
            const error: ErrorResponse = await response.json();
            throw new Error(error.message || 'Ошибка регистрации');
        }

        return await response.json();
    }

    async login(data: LoginRequest): Promise<JwtResponse> {
        const response = await fetch(`${API_BASE_URL}/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });

        if (!response.ok) {
            const error: ErrorResponse = await response.json();
            throw new Error(error.message || 'Ошибка входа');
        }

        return await response.json();
    }

    async getCurrentUser(): Promise<UserDTO> {
        const token = this.getToken();
        if (!token) {
            throw new Error('Токен не найден');
        }

        const response = await fetch(`${API_BASE_URL}/me`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            if (response.status === 401) {
                this.removeTokens();
            }
            const error: ErrorResponse = await response.json();
            throw new Error(error.message || 'Не удалось получить данные пользователя');
        }

        return await response.json();
    }

    async refreshToken(refreshToken: string): Promise<JwtResponse> {
        const response = await fetch(`${API_BASE_URL}/refresh`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ refreshToken })
        });

        if (!response.ok) {
            throw new Error('Не удалось обновить токен');
        }

        return await response.json();
    }

    setToken(token: string): void {
        localStorage.setItem('access_token', token);
        document.cookie = `auth_token=${token}; path=/; max-age=86400; SameSite=Lax`;
    }

    setRefreshToken(token: string): void {
        localStorage.setItem('refresh_token', token);
    }

    removeTokens(): void {
        localStorage.removeItem('access_token');
        localStorage.removeItem('refresh_token');
        document.cookie = 'auth_token=; path=/; max-age=0;';
    }

    getToken(): string | null {
        return localStorage.getItem('access_token') ||
            document.cookie
                .split('; ')
                .find(row => row.startsWith('auth_token='))
                ?.split('=')[1] || null;
    }

    getRefreshToken(): string | null {
        return localStorage.getItem('refresh_token');
    }
}

export default new AuthService();