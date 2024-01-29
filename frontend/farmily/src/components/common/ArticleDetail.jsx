import { useState, useEffect } from 'react';

export default function ArticleDetail(props) {
  return (
    <>
      <div className="w-10/12 h-4/6 mx-auto">
        <div className="border border-stone-500 rounded p-2 mb-4 w-full">
          <strong>{props.title}</strong>
        </div>
        <div className="border border-stone-500 rounded p-2 w-full h-60">
          <p>{props.content}</p>
        </div>
      </div>
    </>
  );
}
