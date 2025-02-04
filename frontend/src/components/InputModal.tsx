import React from 'react'

interface InputModalProps extends React.InputHTMLAttributes<HTMLInputElement>{
  className?: string
}

export default function InputModal({className, ...rest}: InputModalProps) {
  return (
    <input 
      className={`bg-primary border border-tertiary rounded-xl py-1 px-2 ${className}`}
      {...rest}
    />
  )
}
