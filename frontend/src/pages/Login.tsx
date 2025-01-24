import { FormEvent, useState } from 'react'
import useAuth from '../hooks/useAuth'
import { useNavigate } from 'react-router-dom'
import { User } from '../models/User'
import imgLog1 from '../assets/imagelogin1.png'
import imgLog2 from '../assets/imagelogin2.png'
import chabraLogo from '../assets/chabra_logo.png'

export default function Login() {

    const [email, setEmail] = useState<string>("")
    const [password, setPassword] = useState<string>("")
    const [loading, setLoading] = useState<boolean>(false)
    const { user, login } = useAuth()
    const nav = useNavigate()

    const handleLogin = async (event: FormEvent) => {
        setLoading(true)
        event.preventDefault()
        
        const data: User = {
            email: email,
            password: password
        }
        
        try {
            if (await login(data)) {
                console.log("Login Successfully")
                nav("/Home"); 
            }
            console.log("Login Failed")
        } finally {
            setLoading(false); 
        }
        
    }

 return (
    <main className="flex flex-col gap-10 items-center justify-center min-h-screen">
    <img src={imgLog1} className="absolute bottom-0 left-0 z-0" />
    <img src={imgLog2} className="absolute top-0 right-0 z-0" />


    <img src={chabraLogo} alt="" className="h-90" />

    <form 
        onSubmit={handleLogin}
        className='flex flex-col z-10 items-center gap-6 w-[500px]'
    >

        <input 
            className="w-full h-16 px-4 text-2xl border border-gray-800 rounded-lg" 
            placeholder="Email"
            type="email" 
            required
            value={email}
            onChange={(e) => setEmail(e.target.value)}
        />
        <input 
            className="w-full h-16 px-4 text-2xl border border-gray-800 rounded-lg" 
            placeholder='Senha'
            type="password"
            required
            value={password}
            onChange={(e)=>(setPassword(e.target.value))} 
        />
        <button 
            className="w-[300px] h-16 text-2xl bg-[#00A859] hover:bg-[#008C4A] text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
            disabled={loading}
            type='submit'
        >
            Entrar
        </button>

        
    

    </form>
    
</main>
  )
}
