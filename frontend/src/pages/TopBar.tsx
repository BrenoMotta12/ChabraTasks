import React, { useState } from 'react'
import chabraLogo from '../assets/chabra_logo.png'
import searchIcon from '../assets/search.svg'
import useAuth from '../hooks/useAuth'
import arrow from '../assets/arrow_down.svg'
import userIcon from '../assets/user.svg'
import closeIcon from '../assets/close.svg'

export default function TopBar({ onOpenModal }: { onOpenModal: () => void }) {
    
    const { user, logout } = useAuth()
    const [openUserOptions, setOpenUserOptions] = useState<boolean>(false)

    return (
        <div className='flex justify-between w-full top-0 px-6 py-4'>

            <img src={chabraLogo} alt="" className='h-15'/>

            <div className='flex w-140 bg-secondary rounded-2xl border-tertiary border-2 px-4 items-center'>
                <img src={searchIcon} alt="" className='h-5'/>
                <input 
                    type="text" 
                    className='w-full ps-4 h-full focus:outline-none'
                    placeholder='Pesquisar...'
                />
            </div>

            <div className="flex gap-2 relative">
                <h1 className="bg-[#02A85C] text-white font-medium text-4xl rounded-full h-15 w-15 flex items-center justify-center">
                    {user?.name?.charAt(0).toUpperCase()}
                </h1>
                
                <img 
                    src={arrow}
                    onClick={()=>setOpenUserOptions(!openUserOptions)}
                    className={`transition-transform ${openUserOptions ? "rotate-180" : ""}`}
                />
                
                {
                    openUserOptions && 
                    <div className="border absolute top-16 right-0 border-tertiary bg-primary p-3 rounded-3xl z-10 flex flex-col gap-4 min-w-max">
                        
                        <div className='flex items-center gap-4'>
                            <h1 className="bg-[#02A85C] text-white font-medium text-4xl rounded-full h-15 w-15 flex items-center justify-center">
                                {user?.name?.charAt(0).toUpperCase()}
                            </h1>
                            <div className='flex flex-col leading-4'>
                                <h2>{user?.name}</h2>
                                <p className='text-[#7F7F7F]'>on-line</p>
                            </div>
                        </div>

                        <div className='flex flex-col pe-5 gap-2'>
                            <div 
                                className='flex gap-2'
                                onClick={onOpenModal}
                            >
                                <img src={userIcon} />
                                <p className='whitespace-nowrap overflow-hidden'>Cadastro de usuário</p>
                            </div>
                            <div 
                                className='flex gap-2'
                                onClick={logout}
                            >
                                <img src={closeIcon} />
                                <p>Sair</p>
                            </div>
                        </div>
                       
                    </div>

                }
                
            </div>

        </div>
    )
}
