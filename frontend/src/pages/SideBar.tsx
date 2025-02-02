import React, { useEffect, useState } from 'react'
import homeIcon from '../assets/home.svg'
import bellIcon from '../assets/bell.svg'
import graphIcon from '../assets/graph.svg'
import SideBarOption from '../components/SideBarOption'
import openIcon from '../assets/box_to_open.svg'
import closeIcon from '../assets/box_to_close.svg'
import { Api, getHeadersAuthorization, getAuthorization } from '../services/Api'
import useAuth from '../hooks/useAuth'
import axios from 'axios'

export default function SideBar() {

    const [isOpen, setIsOpen] = useState(true)
    const { user } = useAuth()

    

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        if (!user?.token) return; 

        try {
            const response = await Api.get('space', getAuthorization(user.token));
            console.log(response.data);
        } catch (error) {
            console.error("Erro ao buscar dados:", error);
        }
    };

    return (
        <main className={isOpen ? 'w-100' : 'w-20'}>
            
            <div className={`flex flex-col gap-2 ps-4 pb-5 relative ${isOpen ? 'pe-13' : 'pe-4'}`}>

                <SideBarOption
                    text='Início'
                    icon={homeIcon}
                    link='/home'
                    sideBarOpen={isOpen}
                />

                <SideBarOption
                    text='Notificações'
                    icon={bellIcon}
                    link='/notifications'
                    sideBarOpen={isOpen}
                />

                <SideBarOption
                    text='Gráficos'
                    icon={graphIcon}
                    link='/graphs'
                    sideBarOpen={isOpen}
                />

                {isOpen ? 
                    <img 
                        src={closeIcon} 
                        alt="" 
                        className='absolute right-1 h-11 cursor-pointer bg-secondary rounded-xl p-2 border-tertiary border-1' 
                        onClick={() => setIsOpen(!isOpen)}
                    />
                    :
                    <img 
                        src={openIcon} 
                        alt="" 
                        className='h-11 w-11 cursor-pointer bg-secondary rounded-xl p-2 border-tertiary border-1' 
                        onClick={() => setIsOpen(!isOpen)}
                    />
                }
            </div>

            <div className='bg-tertiary w-full h-[1px]'></div>

            
        </main>
  )
}
