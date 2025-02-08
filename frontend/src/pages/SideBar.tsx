import React, { useEffect, useRef, useState } from 'react'
import homeIcon from '../assets/home.svg'
import bellIcon from '../assets/bell.svg'
import graphIcon from '../assets/graph.svg'
import SideBarOption from '../components/SideBarOption'
import openIcon from '../assets/box_to_open.svg'
import closeIcon from '../assets/box_to_close.svg'
import { Api, getHeadersAuthorization, getHeadersBodyAuthorization } from '../services/Api'
import useAuth from '../hooks/useAuth'
import plusIcon from '../assets/plus.svg'
import moreIcon from '../assets/more.svg'
import { List } from '../models/list/List'
import { Space } from '../models/space/Space'
import listIcon from '../assets/list.svg'
import arrowIcon from '../assets/arrow_down.svg'
import { NavLink } from 'react-router-dom'
import { AxiosError } from 'axios'
import { SaveSpace } from '../models/space/SaveSpace'
import InputModal from '../components/InputModal'
import { SaveList } from '../models/list/SaveList'

export default function SideBar() {

    const [isOpen, setIsOpen] = useState(true)
    const { user, logout } = useAuth()
    const [spaces, setSpaces] = useState<Space[]>([]); 
    const [openSpaces, setOpenSpaces] = useState<{ [key: string]: boolean }>({});
    const [modal, setModal] = useState<"addSpace" | "editSpace" | "addList" | "editList" | null>()
    const modalRef = useRef<HTMLDivElement | null>(null);

    // Variaveis para edição e criação do space
    const [nameSpace, setNameSpace] = useState<string>('')
    const [descriptionSpace, setDescriptionSpace] = useState<string>()
    const [colorSpace, setColorSpace] = useState<string>()
    const [editingSpaceId, setEditingSpaceId] = useState<string | null>(null);

     // Variaveis para edição e criação do list
     const [nameList, setNameList] = useState<string>('')
     const [descriptionList, setDescriptionList] = useState<string>()
     const [colorList, setColorList] = useState<string>()
     const [editingListId, setEditingListId] = useState<string | null>(null);

    // Quando o modal é fechado, limpa todos os campos.
    useEffect( ()=> {
        if(modal == "addSpace" || modal == "addList") {
            setNameSpace("")
            setDescriptionSpace("")
            setColorSpace("")
            setNameList("")
            setDescriptionList("")
            setColorList("")
        }

    }, [modal])

    // Fecha o modal quando houver um click fora do mesmo
    useEffect(() => {
        function handleClickOutside(event: MouseEvent) {
            if (modalRef.current && !modalRef.current.contains(event.target as Node)) {
                setModal(null);
            }
        }

        if (modal) {
            setTimeout(() => { // Espera um tick para evitar conflito com o clique no botão
                document.addEventListener("click", handleClickOutside);
            }, 0);
        }

        return () => {
            document.removeEventListener("click", handleClickOutside);
        };
    }, [modal]);

    

    // Quando modal de editspace abrir, deve buscar os dados do SPACE a ser editado
    useEffect(()=>{
        if(modal!="editSpace") return

        const getSpaceById = async() => {
            const response = await Api.get(`/space/${editingSpaceId}`, getHeadersAuthorization(user?.token))
            const space: Space = response.data
            setNameSpace(space.name)
            setDescriptionSpace(space.description)
            setColorSpace(space.color)
        }

        try {
            getSpaceById()
        } catch(e) {
            if(e instanceof AxiosError) if(e.response?.status == 403) logout()
            console.log(e)
        }

    },[editingSpaceId, modal])

    // Quando modal de editspace abrir, deve buscar os dados do SPACE a ser editado
    useEffect(()=>{
        if(modal!="editList") return

        const getListById = async() => {
            const response = await Api.get(`/list/${editingListId}`, getHeadersAuthorization(user?.token))
            const list: List = response.data
            setNameList(list.name)
            setDescriptionList(list.description)
            setColorList(list.color)
        }

        try {   
            getListById()
        } catch(e) {
            if(e instanceof AxiosError) if(e.response?.status == 403) logout()
            console.log(e)
        }

    },[editingListId, modal])

    

     // Obtem da API os dados de espaços e listas
    useEffect(() => {
        if (user?.token) {
            fetchSpacesAndLists();
        }
    }, [user?.token]);
    
    const fetchSpacesAndLists = async () => {
        try {
            const [spacesResponse, listsResponse] = await Promise.all([
                Api.get('space', getHeadersAuthorization(user?.token)),
                Api.get('list', getHeadersAuthorization(user?.token))
            ]);
            console.log(spacesResponse.data)
            if(spacesResponse.status !== 200 || listsResponse.status !== 200) {
                logout()
            }
    
            const spacesWithLists = spacesResponse.data.map((space: Space) => ({
                ...space,
                lists: listsResponse.data.filter((list: List) => list.spaceId === space.id)
            }));
    
            setSpaces(spacesWithLists);
        } catch (error) {

            if(error instanceof AxiosError) if(error.response?.status == 403) logout()
            
            
            console.error("Erro ao buscar dados:", error);
        }
    };

    const toggleLists = (spaceId: string) => {
        setOpenSpaces((prev) => ({
            ...prev,
            [spaceId]: !prev[spaceId], // Alterna entre mostrar e ocultar
        }));
    };

    // Lida com a adiçao de um novo espaço ou lista
    async function handleCreate(typeData: "list" | "space"){

        setModal(null)

        if(!user?.token) return

        let data: SaveSpace | SaveList

        if(typeData == "space"){
            data = {
                color: colorSpace,
                description: descriptionSpace,
                name: nameSpace
            }
        } else {

            console.log("criando lista")
            if(!editingSpaceId) return

            data = {
                color: colorList,
                description: descriptionList,
                name: nameList,
                spaceId: editingSpaceId
            }
        }
        
        try {
            const response = await Api.post(typeData == "space" ? '/space' : '/list', data, getHeadersBodyAuthorization(user?.token))
            console.log(response.data)
            fetchSpacesAndLists()
        } catch(error) {
            if(error instanceof AxiosError) if(error.response?.status == 403) logout()
        }
        
    }

    async function handleUpdate(dataType: 'space' | 'list') {

        setModal(null)
        let data: List | Space | null = null

        if(!user?.token) return
        if(dataType == 'space' && editingSpaceId) {
            data = {
                id: editingSpaceId,
                color: colorSpace,
                description: descriptionSpace,
                name: nameSpace
            }
    
        }
        if(dataType == 'list' && editingListId && editingSpaceId) {
            data = {
                id: editingListId,
                color: colorList,
                description: descriptionList,
                name: nameList,
                spaceId: editingSpaceId
            }
        }

        if(!data) return
        
        try {
            await Api.put(dataType == 'space' ? '/space' : '/list', data, getHeadersBodyAuthorization(user?.token))
            fetchSpacesAndLists()
        } catch(error) {
            if(error instanceof AxiosError) if(error.response?.status == 403) logout()
        }

    }

    return (
        <main className={`max-h-screen flex flex-col ${isOpen ? 'w-100' : 'w-20'}`} 
        >
            
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

            {/*Div espaço*/}
            <div className='flex w-full overflow-hidden'>
                <div className='flex flex-col gap-2 ps-4 pe-2 relative w-full'>
                    { isOpen &&
                    <div 
                        className='flex justify-between content-center mb-2 relative'
                        onClick={(e) => e.stopPropagation()}
                    >
                        <h1>Espaços</h1>
                        <div 
                            className='flex gap-2'
                            
                        >

                            {user?.role == "ADMIN" &&
                                <img 
                                    src={plusIcon} 
                                    className={`w-6 p-1 cursor-pointer rounded-full ${modal === 'addSpace' ? 'bg-secondary border border-tertiary' : ''}`}
                                    onClick={() => { modal !== "addSpace" ? setModal("addSpace") : setModal(null) }}
                                />
                            }
                            

                            {/* Modal de adição de espaço*/}
                            {modal === 'addSpace' &&
                                <div
                                    className='absolute top-6 right-3 bg-secondary border border-tertiary rounded-xl flex flex-col justify-center items-center p-3 z-10'
                                    ref={modalRef}
                                >
                                    <h1 className='w-full text-center'>Adicionar Espaço</h1>
                                    <form 
                                        onSubmit={()=>handleCreate("space")}
                                        className='flex flex-col items-center'
                                    >
                                        <InputModal
                                            required={true}
                                            type='text'
                                            placeholder='Nome'
                                            value={nameSpace}
                                            onChange={(e)=>(setNameSpace(e.target.value))}
                                            className='mt-2'
                                        />

                                        <InputModal
                                            type='text'
                                            placeholder='Descrição'
                                            value={descriptionSpace}
                                            onChange={(e)=>(setDescriptionSpace(e.target.value))}
                                            className='mt-2'
                                        />
                                        <div className='flex gap-2 mt-2 items-center text-[#7F7F7F]'>
                                            <p>Selecione a cor: </p>
                                            <input
                                                type='color'
                                                value={colorSpace}
                                                onChange={(e)=>(setColorSpace(e.target.value))}
                                                className='w-8'
                                            />
                                        </div>
                                        <button
                                            type='submit'
                                            className='bg-[#02A85C] text-white rounded-xl w-fit py-1 px-2 mt-2'
                                        >
                                            Salvar
                                        </button>
                                    </form>

                                </div>
                            }
                        </div>
                    </div>
                    }
                    <div className='flex flex-col flex-1 overflow-y-auto overflow-x-hidden'>
                        {spaces.sort((a, b) => a.name.localeCompare(b.name)).map((space: Space) => (
                            <div key={space.id} className="mb-2">
                                
                                    
                                <div className="flex justify-between w-full h- max-h-fit ps-1 ">
                                <NavLink
                                    className={({ isActive }) =>
                                        isActive && isOpen ? "bg-bg-selected text-selected rounded-xl w-full" : "text-text-primary w-full "
                                    }
                                    to={`/space/${space.id}`}
                                >
                                    <div className="flex items-center">
                                        <h1
                                            className={` text-white font-medium text-2xl rounded-xl h-8 w-8 flex items-center justify-center`}
                                            style={{ backgroundColor: space.color ? space.color : "#7F7F7F" }}
                                        >
                                            {space.name?.charAt(0).toUpperCase()}
                                        </h1>
                                        {isOpen && <h1 className="ms-2">{space.name}</h1>}
                                    </div>
                                </NavLink>
                                    { isOpen &&
                                    <div className="flex items-center gap-2 mx-4 justify-between max-w-full ">
                                        <div 
                                            className='relative h-full items-center justify-center flex'
                                            onClick={()=> {
                                                setModal('editSpace')
                                                setEditingSpaceId(space.id)
                                            }}
                                            
                                        >

                                            {user?.role == "ADMIN" &&
                                                <img 
                                                    src={moreIcon} 
                                                    className={`h-6 w-9 p-1 ${modal === 'editSpace' && editingSpaceId === space.id ? 'bg-secondary rounded-full border border-tertiary' : ''}`}
                                                
                                                />
                                            }
                                            

                                            {/* Modal de edição de espaço */}
                                            {modal === 'editSpace' && editingSpaceId === space.id && (
                                                <div 
                                                    className='absolute top-7 right-[-40px] bg-secondary border border-tertiary rounded-xl flex flex-col justify-center items-center p-3 z-10 text-text-primary'
                                                    ref={modalRef}
                                                >
                                                    <h1 className='w-full text-center'>Editar Espaço</h1>
                                                    <form className='flex flex-col items-center'
                                                        onSubmit={()=>handleUpdate('space')}
                                                    >
                                                        <InputModal
                                                            type='text'
                                                            placeholder='Nome'
                                                            required={true}
                                                            value={nameSpace}
                                                            onChange={(e) => setNameSpace(e.target.value)}
                                                            className='mt-2'
                                                        />
                                                        <InputModal
                                                            type='text'
                                                            placeholder='Descrição'
                                                            value={descriptionSpace}
                                                            onChange={(e) => setDescriptionSpace(e.target.value)}
                                                            className='mt-2'
                                                        />
                                                        <div className='flex gap-2 mt-2 items-center text-[#7F7F7F]'>
                                                            <p>Selecione a cor: </p>
                                                            <input
                                                                type='color'
                                                                value={colorSpace}
                                                                onChange={(e) => setColorSpace(e.target.value)}
                                                                className='w-8 cursor-pointer pointer-events-auto'
                                                            />
                                                        </div>
                                                        <button type='submit' className='bg-[#02A85C] text-white rounded-xl w-fit py-1 px-2 mt-2'>
                                                            Salvar
                                                        </button>
                                                        
                                                    </form>
                                                </div>
                                            )}

                                        </div>

                                        <div 
                                            className='relative h-full items-center flex'
                                            onClick={()=> {
                                                setModal('addList')
                                                setEditingSpaceId(space.id)
                                            }}
                                        >

                                            {user?.role == "ADMIN" &&
                                                <img 
                                                    src={plusIcon} 
                                                    className={`w-9 h-6 p-1 cursor-pointer rounded-full ${modal === 'addList' && editingSpaceId == space.id ? 'bg-secondary border border-tertiary' : ''}`}
                                                    onClick={() => { modal !== "addList" ? setModal("addList") : setModal(null) }}
                                            />}

                                            {/* Modal de adição de lista */}
                                            {modal === 'addList' &&  editingSpaceId === space.id &&
                                                <div
                                                    className='absolute top-7 right-0 bg-secondary border border-tertiary rounded-xl flex flex-col justify-center items-center p-3 z-10'
                                                    ref={modalRef}
                                                >
                                                    <h1 className='w-full text-center'>Adicionar Lista</h1>
                                                    <form 
                                                        onSubmit={()=>handleCreate("list")}
                                                        className='flex flex-col items-center'
                                                    >
                                                        <InputModal
                                                            required={true}
                                                            type='text'
                                                            placeholder='Nome'
                                                            value={nameList}
                                                            onChange={(e)=>(setNameList(e.target.value))}
                                                            className='mt-2'
                                                        />

                                                        <InputModal
                                                            type='text'
                                                            placeholder='Descrição'
                                                            value={descriptionList}
                                                            onChange={(e)=>(setDescriptionList(e.target.value))}
                                                            className='mt-2'
                                                        />
                                                        <div className='flex gap-2 mt-2 items-center text-[#7F7F7F]'>
                                                            <p>Selecione a cor: </p>
                                                            <input
                                                                type='color'
                                                                value={colorList}
                                                                onChange={(e)=>(setColorList(e.target.value))}
                                                                className='w-8'
                                                            />
                                                        </div>
                                                        <button
                                                            type='submit'
                                                            className='bg-[#02A85C] text-white rounded-xl w-fit py-1 px-2 mt-2'
                                                        >
                                                            Salvar
                                                        </button>
                                                    </form>

                                                </div>
                                            }
                                        </div>


                                        {space.lists && space.lists?.length > 0 && (
                                            <img
                                                src={arrowIcon}
                                                alt="Toggle Lists"
                                                className={`cursor-pointer transition-transform w-5 p-1 ${
                                                    openSpaces[space.id] ? "rotate-180" : ""
                                                }`}
                                                onClick={() => toggleLists(space.id)}
                                            />
                                        )}
                                    </div>
                                }
                                </div>
                                
                                {/* Exibição das listas */}
                                {openSpaces[space.id] && isOpen &&
                                    space.lists?.sort((a, b) => a.name.localeCompare(b.name)).map((list: List) => (
                                        <div className='flex justify-between ps-10 pe-4 p-2 items-center'>
                                            <NavLink
                                                className={({ isActive }) =>
                                                    isActive ? "bg-bg-selected rounded-xl" : ""
                                                }
                                                style={{color: list.color}}
                                                key={list.id}
                                                to={`/list/board/${list.id}`}
                                            >
                                                <div className="flex items-center gap-2 py-1 w-full px-2">
                                                    <img src={listIcon} className="h-4" />
                                                    <h1>{list.name}</h1>
                                                </div>
                                            </NavLink>

                                            <div 
                                                className='relative'
                                                onClick={()=>{
                                                    setModal('editList')
                                                    setEditingListId(list.id)
                                                    setEditingSpaceId(space.id)
                                                }}
                                            >
                                                {
                                                    user?.role == "ADMIN" &&
                                                    <img 
                                                    src={moreIcon} 
                                                    className={`w-6 h-6 p-1 ${modal === 'editList' && editingListId === list.id ? 'bg-secondary rounded-full border border-tertiary' : ''}`}
                                                    />
                                                }
                                                {/* Modal de edição de lista */}
                                                {modal === 'editList' &&  editingListId === list.id &&
                                                    <div
                                                        className='absolute top-6 right-0 bg-secondary border border-tertiary rounded-xl flex flex-col justify-center items-center p-3 z-10'
                                                        ref={modalRef}
                                                    >
                                                        <h1 className='w-full text-center'>Editar Lista</h1>
                                                        <form 
                                                            onSubmit={()=>handleUpdate("list")}
                                                            className='flex flex-col items-center'
                                                        >
                                                            <InputModal
                                                                required={true}
                                                                type='text'
                                                                placeholder='Nome'
                                                                value={nameList}
                                                                onChange={(e)=>(setNameList(e.target.value))}
                                                                className='mt-2'
                                                            />

                                                            <InputModal
                                                                type='text'
                                                                placeholder='Descrição'
                                                                value={descriptionList}
                                                                onChange={(e)=>(setDescriptionList(e.target.value))}
                                                                className='mt-2'
                                                            />
                                                            <div className='flex gap-2 mt-2 items-center text-[#7F7F7F]'>
                                                                <p>Selecione a cor: </p>
                                                                <input
                                                                    type='color'
                                                                    value={colorList}
                                                                    onChange={(e)=>(setColorList(e.target.value))}
                                                                    className='w-8'
                                                                />
                                                            </div>
                                                            <button
                                                                type='submit'
                                                                className='bg-[#02A85C] text-white rounded-xl w-fit py-1 px-2 mt-2'
                                                            >
                                                                Salvar
                                                            </button>
                                                        </form>

                                                    </div>
                                                }
                                            </div>
                                        </div>
                                    ))}
                            </div>
                        ))}
                    </div>
                </div>                                    
            </div>

        </main>
  )
}
