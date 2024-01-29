import { useState, useEffect } from 'react';

export default function CreateDetail({ title, content, onInputChange }) {
  return (
    <div className="flex flex-col items-center h-full">
      <form className="p-10 w-full h-full">
        <input
          type="text"
          name="title"
          value={title}
          onChange={onInputChange}
          placeholder="제목을 입력하세요."
          className="border-4 border-black rounded-lg p-2 mb-4 w-full"
        />
        <textarea
          placeholder="내용을 입력하세요."
          name="content"
          value={content}
          onChange={onInputChange}
          className="border-4 border-black rounded-lg p-2 w-full h-5/6 resize-none"
        ></textarea>
      </form>
    </div>
  );
}
