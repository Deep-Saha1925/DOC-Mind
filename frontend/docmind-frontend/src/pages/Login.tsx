import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Brain, Lock, User, ArrowRight, Loader2, Sparkles, Eye, EyeOff } from 'lucide-react';
import { motion } from 'framer-motion';
import toast from 'react-hot-toast';
import { useAuthStore } from '../store/authStore';

const Login: React.FC = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [loading, setLoading] = useState(false);
  const [touched, setTouched] = useState({ username: false, password: false });

  const navigate = useNavigate();
  const { login, token } = useAuthStore();

  useEffect(() => {
    if (token) {
      navigate('/', { replace: true });
    }
  }, [token, navigate]);

  const usernameError = touched.username && !username.trim() ? 'Username is required' : '';
  const passwordError = touched.password && !password.trim() ? 'Password is required' : '';

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setTouched({ username: true, password: true });

    if (!username.trim() || !password.trim()) {
      toast.error('Please fill in all fields');
      return;
    }

    setLoading(true);
    try {
      await login(username.trim(), password);
      toast.success('Welcome back!');
      navigate('/', { replace: true });
    } catch (err: unknown) {
      const errorMsg =
        (err as { response?: { data?: { message?: string } } })?.response?.data?.message ||
        (err as Error).message ||
        'Invalid username or password';
      toast.error(errorMsg);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-ink-950 flex flex-col justify-center py-12 px-4 sm:px-6 lg:px-8 relative overflow-hidden">
      {/* Background glowing effects */}
      <div className="absolute top-1/4 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[550px] h-[550px] bg-gradient-to-tr from-accent-600/15 via-sky-600/10 to-transparent blur-[140px] rounded-full pointer-events-none" />

      <motion.div
        initial={{ opacity: 0, y: 16 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.25 }}
        className="sm:mx-auto sm:w-full sm:max-w-md relative z-10"
      >
        <div className="flex justify-center items-center gap-3 mb-4">
          <div className="w-12 h-12 bg-accent-400 rounded-2xl flex items-center justify-center">
            <Brain className="w-7 h-7 text-ink-950" />
          </div>
          <div>
            <div className="flex items-center gap-1.5">
              <span className="font-display text-4xl text-white tracking-tight">DocMind</span>
              <span className="text-xs font-semibold text-accent-400 bg-accent-500/10 border border-accent-500/20 px-2 py-0.5 rounded-full">
                AI
              </span>
            </div>
            <p className="text-xs text-ink-400">Intelligent Document Assistant</p>
          </div>
        </div>

        <h2 className="mt-6 text-center font-display text-4xl tracking-tight text-white">
          Sign in to your account
        </h2>
        <p className="mt-2 text-center text-sm text-ink-400">
          Or{' '}
          <Link
            to="/register"
            className="font-medium text-accent-400 hover:text-accent-300 transition-colors underline underline-offset-4 focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-accent-500 rounded"
          >
            create a new account
          </Link>
        </p>
      </motion.div>

      <motion.div
        initial={{ opacity: 0, scale: 0.98 }}
        animate={{ opacity: 1, scale: 1 }}
        transition={{ duration: 0.25, delay: 0.05 }}
        className="mt-8 sm:mx-auto sm:w-full sm:max-w-md relative z-10"
      >
        <div className="bg-ink-900/90 py-8 px-6 shadow-2xl border border-ink-800 sm:rounded-2xl sm:px-10 backdrop-blur-xl">
          <form className="space-y-5" onSubmit={handleSubmit} noValidate>
            <div>
              <label htmlFor="login-username" className="block text-xs font-medium text-ink-300 mb-1.5">
                Username
              </label>
              <div className="relative rounded-xl shadow-sm">
                <div className="absolute inset-y-0 left-0 pl-3.5 flex items-center pointer-events-none text-ink-500">
                  <User className="h-4 w-4" />
                </div>
                <input
                  id="login-username"
                  type="text"
                  autoComplete="username"
                  value={username}
                  onBlur={() => setTouched((prev) => ({ ...prev, username: true }))}
                  onChange={(e) => setUsername(e.target.value)}
                  placeholder="Enter your username"
                  className="block w-full pl-10 pr-3 py-2.5 bg-ink-950 border border-ink-800 rounded-xl text-white placeholder-ink-500 focus:outline-none focus:border-accent-500 focus:ring-1 focus:ring-accent-500 text-sm transition-all"
                />
              </div>
              {usernameError && (
                <p className="text-xs text-red-400 mt-1.5 pl-1">{usernameError}</p>
              )}
            </div>

            <div>
              <label htmlFor="login-password" className="block text-xs font-medium text-ink-300 mb-1.5">
                Password
              </label>
              <div className="relative rounded-xl shadow-sm">
                <div className="absolute inset-y-0 left-0 pl-3.5 flex items-center pointer-events-none text-ink-500">
                  <Lock className="h-4 w-4" />
                </div>
                <input
                  id="login-password"
                  type={showPassword ? 'text' : 'password'}
                  autoComplete="current-password"
                  value={password}
                  onBlur={() => setTouched((prev) => ({ ...prev, password: true }))}
                  onChange={(e) => setPassword(e.target.value)}
                  placeholder="••••••••"
                  className="block w-full pl-10 pr-10 py-2.5 bg-ink-950 border border-ink-800 rounded-xl text-white placeholder-ink-500 focus:outline-none focus:border-accent-500 focus:ring-1 focus:ring-accent-500 text-sm transition-all"
                />
                <button
                  type="button"
                  onClick={() => setShowPassword(!showPassword)}
                  className="absolute inset-y-0 right-0 pr-3.5 flex items-center text-ink-500 hover:text-ink-300 focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-accent-500 rounded"
                  aria-label={showPassword ? 'Hide password' : 'Show password'}
                >
                  {showPassword ? <EyeOff className="w-4 h-4" /> : <Eye className="w-4 h-4" />}
                </button>
              </div>
              {passwordError && (
                <p className="text-xs text-red-400 mt-1.5 pl-1">{passwordError}</p>
              )}
            </div>

            <div>
              <button
                type="submit"
                disabled={loading}
                className="w-full flex justify-center items-center gap-2 py-2.5 px-4 border border-transparent rounded-xl text-sm font-semibold text-ink-950 bg-accent-400 hover:bg-accent-300 active:scale-[0.98] focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-offset-2 focus-visible:ring-accent-500 disabled:opacity-50 disabled:cursor-not-allowed  transition-all cursor-pointer"
              >
                {loading ? (
                  <>
                    <Loader2 className="w-4 h-4 animate-spin" />
                    Signing in…
                  </>
                ) : (
                  <>
                    Sign in
                    <ArrowRight className="w-4 h-4" />
                  </>
                )}
              </button>
            </div>
          </form>

          <div className="mt-6 pt-5 border-t border-ink-800">
            <div className="flex items-center justify-center gap-2 text-xs text-ink-400">
              <Sparkles className="w-3.5 h-3.5 text-accent-400" />
              <span>RAG Q&A with Semantic Search & Vector Memory</span>
            </div>
          </div>
        </div>
      </motion.div>
    </div>
  );
};

export default Login;
