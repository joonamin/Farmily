import { useState, useEffect } from 'react';

import CommentList from './CommentList.jsx';
import CommentCreate from './CommentCreate.jsx';

export default function Comment() {
  return (
    <div className="w-10/12 h-4/6 mx-auto">
      <CommentCreate />
      <CommentList />
    </div>
  );
}
