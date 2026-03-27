import React, {useState} from 'react';
import type {RegisterRequest} from '../../models/types/auth.types.ts';
import type {UserAddDTO} from '../../models/UserAddDTO.ts';
import authService from '../../api/authservice.ts';
import '../../styles/auth/RegisterForm.css';

interface RegisterFormProps {
  onSuccess?: () => void;
}

interface FormState {
  login: string;
  email: string;
  fullName: string;
  phone: string;
  birthday: string;
  password: string;
  confirmPassword: string;
  rememberMe: boolean;
}

const RegisterForm: React.FC<RegisterFormProps> = ({ onSuccess }) => {

  const [formData, setFormData] = useState<FormState>({
    login: '',
    email: '',
    fullName: '',
    phone: '',
    birthday: '',
    password: '',
    confirmPassword: '',
    rememberMe: false,
  });

  const [isLoading, setIsLoading] = useState(false);

  const [error, setError] = useState<string | null>(null);
  const [fieldErrors, setFieldErrors] = useState<Record<string, string>>({});

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value, type, checked } = e.target;

    setFormData(prev => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value,
    }));

    if (fieldErrors[name]) {
      setFieldErrors(prev => {
        const newErrors = { ...prev };
        delete newErrors[name];
        return newErrors;
      });
    }
  };

  const validate = (): boolean => {
    const errors: Record<string, string> = {};

    if (!formData.login.trim()) {
      errors.login = 'Логин обязателен';
    } else if (formData.login.length < 3) {
      errors.login = 'Логин должен быть не менее 3 символов';
    }

    if (!formData.email.trim()) {
      errors.email = 'Email обязателен';
    } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email)) {
      errors.email = 'Неверный формат email';
    }

    if (!formData.password) {
      errors.password = 'Пароль обязателен';
    } else if (formData.password.length < 6) {
      errors.password = 'Пароль должен быть не менее 6 символов';
    }

    if (formData.password !== formData.confirmPassword) {
      errors.confirmPassword = 'Пароли не совпадают';
    }

    setFieldErrors(errors);
    return Object.keys(errors).length === 0;
  };

  const prepareRequestData = (): RegisterRequest => {
    const newUser: UserAddDTO = {
      login: formData.login,
      email: formData.email,
      fullName: formData.fullName,
      phone: formData.phone,
      birthday: formData.birthday,
    };

    return {
      password: formData.password,
      rememberMe: formData.rememberMe,
      newUser: newUser,
    };
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!validate()) {
      return;
    }

    setIsLoading(true);
    setError(null);

    try {
      const requestData = prepareRequestData();
      const response = await authService.register(requestData);

      authService.setToken(response.accessToken);
      if (response.refreshToken) {
        authService.setRefreshToken(response.refreshToken);
      }

      console.log('Registration successful:', response);

      if (onSuccess) {
        onSuccess();
      }

    } catch (err) {
      setError(err instanceof Error ? err.message : 'Ошибка регистрации');
    } finally {
      setIsLoading(false);
    }
  };

  return (
      <form onSubmit={handleSubmit} className="register-form">
        <h2 style={{marginTop:'350px', fontSize:'40px'}}>Регистрация</h2>
        <p style={{
          textAlign: 'center',
          color: '#64748b',
          marginTop: '-15px',
          marginBottom: '30px',
          fontSize: '14px'
        }}>
          Создайте новый аккаунт
        </p>

        {error && (
            <div className="error-message">
              {error}
            </div>
        )}

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

        <div className="form-group">
          <label htmlFor="email">Email *</label>
          <input
              type="email"
              id="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              className={fieldErrors.email ? 'error' : ''}
              placeholder="example@mail.com"
          />
          {fieldErrors.email && (
              <span className="field-error">{fieldErrors.email}</span>
          )}
        </div>

        <div className="form-group">
          <label htmlFor="fullName">Полное имя</label>
          <input
              type="text"
              id="fullName"
              name="fullName"
              value={formData.fullName}
              onChange={handleChange}
              placeholder="Иван Иванов"
          />
        </div>

        <div className="form-group">
          <label htmlFor="phone">Телефон</label>
          <input
              type="tel"
              id="phone"
              name="phone"
              value={formData.phone}
              onChange={handleChange}
              placeholder="+7 (999) 123-45-67"
          />
        </div>

        <div className="form-group">
          <label htmlFor="birthday">Дата рождения</label>
          <input
              type="date"
              id="birthday"
              name="birthday"
              value={formData.birthday}
              onChange={handleChange}
          />
        </div>

        <div className="form-group">
          <label htmlFor="password">Пароль *</label>
          <input
              type="password"
              id="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              className={fieldErrors.password ? 'error' : ''}
              placeholder="Минимум 6 символов"
          />
          {fieldErrors.password && (
              <span className="field-error">{fieldErrors.password}</span>
          )}
        </div>

        <div className="form-group">
          <label htmlFor="confirmPassword">Подтвердите пароль *</label>
          <input
              type="password"
              id="confirmPassword"
              name="confirmPassword"
              value={formData.confirmPassword}
              onChange={handleChange}
              className={fieldErrors.confirmPassword ? 'error' : ''}
              placeholder="Повторите пароль"
          />
          {fieldErrors.confirmPassword && (
              <span className="field-error">{fieldErrors.confirmPassword}</span>
          )}
        </div>

        <div className="checkbox">
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

        <button
            type="submit"
            disabled={isLoading}
            className="submit-button"
        >
          {isLoading ? 'Регистрация...' : 'Зарегистрироваться'}
        </button>

        <p className="login-link">
          Уже есть аккаунт? <a href="/login">Войти</a>
        </p>
      </form>
  );
};

export default RegisterForm;