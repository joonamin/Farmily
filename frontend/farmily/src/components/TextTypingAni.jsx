import React, { useEffect, useState } from 'react';

const TextTypingAni = ({ text, font }) => {
  const [sequence, setSequence] = useState('');
  const [textCount, setTextCount] = useState(0);
  const [isTypingPaused, setIsTypingPaused] = useState(false);
  const [end, setEnd] = useState(false);
  const [count, setCount] = useState(0);

  useEffect(() => {
    const typingInterval = setInterval(() => {
      if (isTypingPaused) {
        clearInterval(typingInterval);
        setTimeout(() => {
          setIsTypingPaused(false);
          setTextCount(0);
          setSequence('');
        }, 5000);
        return;
      }

      if (textCount >= text.length) {
        setIsTypingPaused(true);
        setEnd(true);
        return;
      }

      const nextChar = text[textCount];
      setSequence((prevSequence) => prevSequence + nextChar);

      if (nextChar === '\n') {
        setTextCount((prevCount) => prevCount + 2);
      } else {
        setTextCount((prevCount) => prevCount + 1);
      }
    }, 50);

    return () => clearInterval(typingInterval);
  }, [text, textCount]);

  return (
    <p className={`${font} landing-p whitespace-pre-line break-normal`}>
      {sequence}
      {end ? null : (
        <span className="inline-block align-top w-0.5 h-[1em] bg-white ml-1 blink" />
      )}
    </p>
  );
};

export default TextTypingAni;
