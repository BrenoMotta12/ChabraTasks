import React from 'react'
import chabraLogo from '../assets/chabra_logo.png'
import searchIcon from '../assets/search.svg'
import useAuth from '../hooks/useAuth'
import arrow from '../assets/arrow_down.svg'

export default function TopBar() {
    
    const { user } = useAuth()

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

            <div className="flex gap-2">
                <h1 className="bg-[#02A85C] text-white font-medium text-4xl rounded-full h-15 w-15 flex items-center justify-center">
                    {user?.name?.charAt(0).toUpperCase()}
                </h1>
                <img src={arrow} alt="" />
            </div>

        </div>
    )
}
