import { useState, useEffect } from 'react';

export default function CreateDetail() {
  return (
    <div className="flex flex-col items-center  h-full">
      <form className=" w-10/12 h-5/6">
        <input
          type="text"
          placeholder="제목"
          className="border border-stone-500 rounded p-2 mb-4 w-full"
        />
        <input
          placeholder="내용"
          className="border border-stone-500 rounded p-2 w-full h-5/6"
        />
      </form>
    </div>
  );
}
