import React, {useState} from "react";
import type {LoginRequest} from "../models/auth.types.ts";
import authservice from "../api/authservice.ts";
import '../styles/LoginForm.css'

interface LoginFormProps {
    onSuccess?: () => void;
}

const LoginForm: React.FC<LoginFormProps> = ({onSuccess}) => {
    const [formData, setFormData] = useState<LoginRequest>({
        login: '',
        password: '',
        rememberMe:false
    });

    const [isLoading, setIsLoading] = useState(false);

    const [error, setError] = useState<string | null>(null);
    const [fieldErrors, setFieldErrors] = useState<Record<string, string>>({});

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const {name, value, type, checked} = e.target;

        setFormData(prev => ({
            ...prev,
            [name]: type === 'checkbox' ? checked : value,
        }));

        if (fieldErrors[name]) {
            setFieldErrors(prev => {
                const newErrors = {...prev};
                delete newErrors[name];
                return newErrors;
            });
        }
    };

    const validate = (): boolean => {
        const errors: Record<string, string>={};
        if(!formData.login.trim()){
            errors.login = 'Login is required'
        }else if(formData.login.length<3){
            errors.login = 'Login must be at least 3 characters'
        }
        if(!formData.password.trim()){
            errors.login = 'Password is required'
        }
        setFieldErrors(errors);
        return Object.keys(errors).length===0;
    }

    const prepareRequestData=():LoginRequest=>{
        return {
            login: formData.login,
            password: formData.password,
            rememberMe:formData.rememberMe
        }
    }

    const handleSubmit = async (e:React.FormEvent)=>{
        e.preventDefault()
        if(!validate()){
            return
        }
        setIsLoading(true);
        setError(null);

        try{
            const requestData = prepareRequestData();
            const response = await authservice.login(requestData);
            if(onSuccess){
                onSuccess();
            }
            console.log('Registration successful:', response);
        } catch (err) {
            setError(err instanceof Error ? err.message : 'Registration failed');
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <form onSubmit={handleSubmit} className="login-form">
            <h2>Вход</h2>

            {}
            {error && (
                <div className="error-message">
                    {error}
                </div>
            )}

            {}
            <div className="form-group">
                <label htmlFor="login">Логин *</label>
                <input
                    type="text"
                    id="login"
                    name="login"
                    value={formData.login}
                    onChange={handleChange}
                    className={fieldErrors.login ? 'error' : ''}
                    placeholder="Введите логин"
                />
                {fieldErrors.login && (
                    <span className="field-error">{fieldErrors.login}</span>
                )}
            </div>
            {}
            <div className="form-group">
                <label htmlFor="password">Пароль *</label>
                <input
                    type="password"
                    id="password"
                    name="password"
                    value={formData.password}
                    onChange={handleChange}
                    className={fieldErrors.password ? 'error' : ''}
                    placeholder="Введите пароль"
                />
                {fieldErrors.password && (
                    <span className="field-error">{fieldErrors.password}</span>
                )}
            </div>
            {}
            <div className="form-group checkbox">
                <label>
                    <input
                        type="checkbox"
                        name="rememberMe"
                        checked={formData.rememberMe}
                        onChange={handleChange}
                    />
                    Запомнить меня
                </label>
            </div>

            {}
            <button
                type="submit"
                disabled={isLoading}
                className="submit-button"
            >
                {isLoading ? 'Вход...' : 'Войти'}
            </button>

            {}
            <p className="login-link">
                Ещё нет аккаунта? <a href="/register">Зарегистрироваться</a>
            </p>
        </form>
    );
}

export default LoginForm;
