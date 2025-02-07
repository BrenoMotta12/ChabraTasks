import React from 'react'

interface InputModalProps extends React.InputHTMLAttributes<HTMLInputElement>{
  className?: string
  label?: string
}

export default function InputModal({className, label, ...rest}: InputModalProps) {
  return (
    <div className='flex justify-between items-center'>
      <label>{label}</label>
      <input 
        className={`bg-primary border border-tertiary rounded-xl py-1 px-2 ${className}`}
        {...rest}
      />
    </div>
  )
}
