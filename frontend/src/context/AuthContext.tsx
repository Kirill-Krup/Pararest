import { createContext, useContext, useState, useEffect, type ReactNode } from "react";
import authService from "../api/authservice.ts";
import type { UserDTO } from "../models/UserDTO.ts";
import type { LoginRequest, RegisterRequest } from "../models/auth.types.ts";

interface AuthContextType {
    user: UserDTO | null;
    isAuthenticated: boolean;
    isLoading: boolean;
    login: (data: LoginRequest) => Promise<void>;
    logout: () => void;
    register: (data: RegisterRequest) => Promise<void>;
    updateUser: (userData: Partial<UserDTO>) => void;
    refreshAuth: () => Promise<void>;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
    const [user, setUser] = useState<UserDTO | null>(null);
    const [isLoading, setIsLoading] = useState(true);

    const loadUser = async (): Promise<void> => {
        try {
            const userData = await authService.getCurrentUser();
            setUser(userData);
        } catch (error) {
            console.error('Failed to load user:', error);
            setUser(null);
        }
    };

    useEffect(() => {
        const initializeAuth = async () => {
            try {
                const token = authService.getToken();
                if (token) {
                    await loadUser();
                }
            } catch (error) {
                console.error('Auth initialization failed:', error);
            } finally {
                setIsLoading(false);
            }
        };

        initializeAuth();
    }, []);

    const login = async (data: LoginRequest): Promise<void> => {
        setIsLoading(true);
        try {
            const response = await authService.login(data);

            authService.setToken(response.accessToken);
            if (response.refreshToken) {
                authService.setRefreshToken(response.refreshToken);
            }

            await loadUser();
        } catch (error) {
            throw error;
        } finally {
            setIsLoading(false);
        }
    };

    const register = async (data: RegisterRequest): Promise<void> => {
        setIsLoading(true);
        try {
            const response = await authService.register(data);

            authService.setToken(response.accessToken);
            if (response.refreshToken) {
                authService.setRefreshToken(response.refreshToken);
            }

            await loadUser();
        } catch (error) {
            throw error;
        } finally {
            setIsLoading(false);
        }
    };

    const logout = () => {
        authService.removeTokens();
        setUser(null);
    };

    const updateUser = (userData: Partial<UserDTO>) => {
        if (user) {
            setUser({ ...user, ...userData });
        }
    };

    const refreshAuth = async (): Promise<void> => {
        try {
            const refreshToken = authService.getRefreshToken();
            if (!refreshToken) {
                throw new Error('Refresh token not found');
            }

            const response = await authService.refreshToken(refreshToken);
            authService.setToken(response.accessToken);
            if (response.refreshToken) {
                authService.setRefreshToken(response.refreshToken);
            }

            await loadUser();
        } catch (error) {
            console.error('Token refresh failed:', error);
            logout();
        }
    };

    return (
        <AuthContext.Provider value={{
            user,
            isAuthenticated: !!user,
            isLoading,
            login,
            logout,
            register,
            updateUser,
            refreshAuth
        }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) throw new Error('useAuth must be used within AuthProvider');
    return context;
};