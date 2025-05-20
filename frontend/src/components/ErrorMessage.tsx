import type { MouseEventHandler } from "react";

export default function ErrorMessage({
  message,
  onRetry,
}: {
  message: string;
  onRetry?: MouseEventHandler;
}) {
  return (
    <>
      <p>{message}</p>
      {onRetry && (
        <button type="button" onClick={onRetry}>
          Reintentar
        </button>
      )}
    </>
  );
}
