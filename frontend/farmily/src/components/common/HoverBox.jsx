import { useState, useEffect } from 'react';

export default function HoverBox({ title }) {
  return (
    <>
      <p className="z-10 border-2 h-7 border-gray-300 bg-white rounded-md px-1 inline-block text-nowrap">
        {title}
      </p>
    </>
  );
}
