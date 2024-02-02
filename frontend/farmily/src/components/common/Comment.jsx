import { useState, useEffect } from 'react';

import CommentList from './CommentList.jsx';
import CommentCreate from './CommentCreate.jsx';

export default function Comment({ comments, recordId, onCommentCreate }) {
  return (
    <div className="w-full h-4/6 mx-auto">
      <CommentCreate
        recordId={recordId}
        comments={comments}
        onCommentCreate={onCommentCreate}
      />
      <CommentList comments={comments} />
    </div>
  );
}
