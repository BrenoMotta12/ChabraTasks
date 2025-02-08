import React, { ReactNode } from 'react'
import boardIcon from '../assets/board.svg'
import listIcon from '../assets/list.svg'
import graphIcon from '../assets/graph.svg'
import activityIcon from '../assets/activity.svg'
import useAuth from '../hooks/useAuth'
import { NavLink } from 'react-router-dom'

interface FragmentNavBarProps {
  id: string | undefined
}

export default function FragmentNavBar({ id }: FragmentNavBarProps) {
  const { user } = useAuth()

  return (
    <main>
      <div className="w-full flex gap-10 h-13 px-5 items-center">
        <NavLink
          to={`/list/board/${id}`}
          className={({ isActive }) =>
            isActive
              ? 'border-b-2 border-black h-full items-center'
              : 'p-2 items-center'
          }
        >
            <div className='flex gap-2 items-center h-full'>
                <img src={boardIcon} alt="" className='h-5' />
                <h2>Quadro</h2>
            </div>
        </NavLink>

        <NavLink
          to={`/list/list/${id}`}
          className={({ isActive }) =>
            isActive
              ? 'border-b-2 border-black h-full items-center'
              : 'p-2 items-center'
          }
        >
            <div className="flex gap-2 items-center h-full">
                <img src={listIcon} alt="" className='h-4' />
                <h2>Lista</h2>
            </div>
          </NavLink>

        <NavLink
          to={`/list/activity/${id}`}
          className={({ isActive }) =>
            isActive
              ? 'border-b-2 border-black h-full items-center'
              : 'p-2 items-center'
          }
        >
            <div className="flex gap-2 items-center h-full">
                <img src={activityIcon} alt="" />
                <h2>Atividade</h2>
            </div>
        </NavLink>

        {user?.role === 'ADMIN' && (
           <NavLink
                to={`/list/graph/${id}`}
                className={({ isActive }) =>
                isActive
                    ? 'border-b-2 border-black h-full items-center'
                    : 'p-2 items-center'
                }
            >
                <div className="flex gap-2 items-center h-full">
                    <img src={graphIcon} alt="" />
                    <h2>Gráficos</h2>
                </div>
            </NavLink>
        )}
      </div>
    </main>
  )
}
