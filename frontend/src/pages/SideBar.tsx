import React, { useEffect, useState } from 'react'
import homeIcon from '../assets/home.svg'
import bellIcon from '../assets/bell.svg'
import graphIcon from '../assets/graph.svg'
import SideBarOption from '../components/SideBarOption'
import openIcon from '../assets/box_to_open.svg'
import closeIcon from '../assets/box_to_close.svg'
import { Api, getHeadersAuthorization, getAuthorization } from '../services/Api'
import useAuth from '../hooks/useAuth'
import plusIcon from '../assets/plus.svg'
import moreIcon from '../assets/more.svg'
import { List } from '../models/List'
import { Space } from '../models/Space'
import listIcon from '../assets/list.svg'
import arrowIcon from '../assets/arrow_down.svg'
import { NavLink } from 'react-router-dom'

export default function SideBar() {

    const [isOpen, setIsOpen] = useState(true)
    const { user } = useAuth()
    const [spaces, setSpaces] = useState<Space[]>([]); 
    const [openSpaces, setOpenSpaces] = useState<{ [key: string]: boolean }>({});

    useEffect(() => {
        if (user?.token) {
            fetchSpacesAndLists();
        }
    }, [user?.token]);
    
    const fetchSpacesAndLists = async () => {
        try {
            const header = {
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${user?.token}`
                }
            };
    
            const [spacesResponse, listsResponse] = await Promise.all([
                Api.get('space', header),
                Api.get('list', header)
            ]);
    
            const spacesWithLists = spacesResponse.data.map((space: Space) => ({
                ...space,
                lists: listsResponse.data.filter((list: List) => list.spaceId === space.id)
            }));
    
            setSpaces(spacesWithLists);
        } catch (error) {
            console.error("Erro ao buscar dados:", error);
        }
    };

    const toggleLists = (spaceId: string) => {
        setOpenSpaces((prev) => ({
            ...prev,
            [spaceId]: !prev[spaceId], // Alterna entre mostrar e ocultar
        }));
    };

    return (
        <main className={isOpen ? 'w-100' : 'w-20'}>
            
            <div className={`flex flex-col gap-2 ps-4 relative ${isOpen ? 'pe-13' : 'pe-4'}`}>

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

            <div className='bg-tertiary w-full h-[1px] my-5'></div>

            <div>
                <div className='flex flex-col gap-2 ps-4 relative'>
                    { isOpen &&
                    <div className='flex justify-between content-center pe-4 mb-2'>
                        <h1>Espaços</h1>
                        <div className='flex gap-2'>
                            <img src={moreIcon} alt="" className='w-4'/>
                            <img src={plusIcon} alt="" className='w-4'/>
                        </div>
                    </div>
                    }

                    {spaces.map((space: Space) => (
                        <div key={space.id} className="mb-2">
                            <NavLink
                                className={({ isActive }) =>
                                    isActive ? "bg-bg-selected text-selected rounded-xl" : "text-text-primary"
                                }
                                to={`/space/${space.id}`}
                            >
                                
                                <div className="flex justify-between w-full h- h-full pe-4 ps-1 ">
                                    <div className="flex items-center">
                                        <h1
                                            className={`bg-[${space.color}] text-white font-medium text-2xl rounded-xl h-8 w-8 flex items-center justify-center`}
                                        >
                                            {space.name?.charAt(0).toUpperCase()}
                                        </h1>
                                        {isOpen && <h1 className="ms-2">{space.name}</h1>}
                                    </div>
                                    { isOpen &&
                                    <div className="flex items-center gap-2">
                                        <img src={moreIcon} alt="" className="w-4" />
                                        <img src={plusIcon} alt="" className="w-4" />
                                        {space.lists?.length > 0 && (
                                            <img
                                                src={arrowIcon}
                                                alt="Toggle Lists"
                                                className={`cursor-pointer transition-transform ${
                                                    openSpaces[space.id] ? "rotate-180" : ""
                                                }`}
                                                onClick={() => toggleLists(space.id)}
                                            />
                                        )}
                                    </div>
                                }
                                </div>
                            </NavLink>

                            {openSpaces[space.id] && isOpen &&
                                space.lists?.map((list: List) => (
                                    <NavLink
                                        className={({ isActive }) =>
                                            isActive ? "bg-bg-selected text-selected rounded-xl" : "text-text-primary"
                                        }
                                        key={list.id}
                                        to={`/list/board/${list.id}`}
                                    >
                                        <div className="flex items-center py-2 gap-2 ps-8">
                                            <img src={listIcon} alt="" className="h-4" />
                                            <h1>{list.name}</h1>
                                        </div>
                                    </NavLink>
                                ))}
                        </div>
                    ))}
                </div>

            </div>

            
        </main>
  )
}
