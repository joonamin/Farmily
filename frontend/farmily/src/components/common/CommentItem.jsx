import { useState, useEffect } from 'react';

export default function CommentItem(comment) {
  return (
    <>
      <div className="mb-2">
        <div className="flex items-start mb-2">
          <span>{comment.id}.</span>
          <span className="ml-2">{comment.author}</span>
        </div>
        <div className="flex items-start mr-0">{comment.content}</div>
        <hr />
      </div>
    </>
  );
}
