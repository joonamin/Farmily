import { useState, useEffect } from 'react';

export default function CommentItem(comment) {
  return (
    <>
      <div className="mb-2 px-4">
        <div className="flex items-start mb-2">
          <span>
            {comment.author.nickname} : {comment.content}
          </span>
        </div>
        <hr />
      </div>
    </>
  );
}
